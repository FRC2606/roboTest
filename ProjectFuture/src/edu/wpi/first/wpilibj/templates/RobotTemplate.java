/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
import java.lang.Math;

public class RobotTemplate extends IterativeRobot {
    Victor leftMotor,rightMotor;
    Joystick leftStick,rightStick;
    RobotDrive roboDR;
    DriverStationLCD screen;
    
    public void robotInit() {
        leftMotor=new Victor(1,1);
        rightMotor=new Victor(1,4);
        roboDR=new RobotDrive(leftMotor,rightMotor);
        screen=DriverStationLCD.getInstance();
        screen.updateLCD();
    }
    
    /**
     * Disabled Functions
     */
    public void disabledInit() {}
    public void disabledPeriodic() {}
    public void disabledContinous() {}
    
    /**
     * Autonomous Functions
     */
    public void autonomousInit() {}
    public void autonomousPeriodic() {}
    public void autonomousContinous() {}

    /**
     * Tele-Operation Functions
     */
    public void teleopInit() {}
    public void teleopPeriodic() {}
    public void teleopContinous() {}
    
    /**
     * Screen Functions
     */
    void teleOpScreen(){}
    void autoModeScreen(){}
    void clearScreen(){
        screen.println(DriverStationLCD.Line.kUser2, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser3, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser4, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser5, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser6, 1,"                     ");
        screen.updateLCD();
    }
    
    /* Custom Functions*/
}
