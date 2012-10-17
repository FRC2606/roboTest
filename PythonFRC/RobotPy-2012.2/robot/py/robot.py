import wpilib
import math

lstick = wpilib.Joystick(1)
rstick = wpilib.Joystick(2)

lmotor = wpilib.Jaguar(1,4)
rmotor = wpilib.Jaguar(1,1)

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
        #left=lstick.GetY()
        #right=-rstick.GetY()
        x=lstick.GetX();
        y=lstick.GetY();
        left=y-x
        right=-1*(y+x)
        cruise=(lstick.GetZ()-1)/2;
        lmotor.Set(left*math.fabs(left)+cruise)
        rmotor.Set(right*math.fabs(right)-cruise)

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
