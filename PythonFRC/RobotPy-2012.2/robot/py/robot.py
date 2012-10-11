import wpilib
import math

lstick = wpilib.Joystick(1)
rstick = wpilib.Joystick(2)

lmotor = wpilib.Jaguar(1,4)
rmotor = wpilib.Jaguar(1,1)
#roboDR = wpilib.RobotDrive(lstick,rstick)

def CheckRestart():
    if lstick.GetRawButton(10):
        raise RuntimeError("Restart")

class MyRobot(wpilib.IterativeRobot):
    def DisabledContinuous(self):
        wpilib.Wait(0.01)
    def AutonomousContinuous(self):
        wpilib.Wait(0.01)
    def TeleopContinuous(self):
        wpilib.Wait(0.01)
        #lmotor.Set(lstick.GetY())
        #rmotor.Set(-rstick.GetY())
        left=lstick.GetY()
        right=-rstick.GetY()
        lmotor.Set(left*math.fabs(left))
        rmotor.Set(right*math.fabs(right))
        #roboDR.ArcadeDrive(lstick.GetY(),lstick.GetX(),true)

    def DisabledPeriodic(self):
        CheckRestart()

    def AutonomousInit(self):
        self.GetWatchdog().SetEnabled(False)

    def AutonomousPeriodic(self):
        CheckRestart()

    def TeleopInit(self):
        dog = self.GetWatchdog()
        dog.SetEnabled(True)
        dog.SetExpiration(0.25)

    def TeleopPeriodic(self):
        self.GetWatchdog().Feed()
        CheckRestart()

        # Motor control

def run():
    robot = MyRobot()
    robot.StartCompetition()
