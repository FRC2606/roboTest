import wpilib

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
        #motor.Set(lstick)

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
        lmotor.Set(lstick.GetY())
        rmotor.Set(rstick.GetY())

def run():
    robot = MyRobot()
    robot.StartCompetition()
