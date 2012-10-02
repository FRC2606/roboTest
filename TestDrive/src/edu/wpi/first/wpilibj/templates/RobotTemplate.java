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
    Victor leftMotor,rightMotor;    //Declares Speed Controllers
    Joystick leftStick,rightStick;  //Delcares Joysticks
    RobotDrive roboDR;              //Declares Robot Drive Logic
    DriverStationLCD screen;        //Delcares Driver Station Screen
    boolean isArcadeDrive;          //Declares Drive Boolean
    double leftSpeed,rightSpeed;    //Declares Drive Speed Variables
    
    public void robotInit() {
        //Sets SpeedCtrlrs to (channel,port)
        leftMotor= new Victor(1,4);
        rightMotor = new Victor(1,1);
        
        //Sets RobotDrive Logic to left and right speedCtrlrs
        roboDR=new RobotDrive(leftMotor,rightMotor);
        
        //Gets screen and sets a pointer it 
        screen=DriverStationLCD.getInstance();
        screen.updateLCD();
        
        //Sets Joysticks to ports
        leftStick=new Joystick(1);
        rightStick=new Joystick(2);
        
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
    public void teleopInit() {
        isArcadeDrive=false;
    }
    public void teleopPeriodic() {
        //Determines Drive through two buttons
        if     (rightStick.getRawButton(6))isArcadeDrive=true;
        else if(rightStick.getRawButton(7))isArcadeDrive=false;
        
        clearScreen();
    }
    public void teleopContinous() {
        if(isArcadeDrive) roboDR.arcadeDrive(rightStick,true);
        else{
            leftSpeed=leftStick.getY();
            rightSpeed=rightStick.getY();
            roboDR.tankDrive(leftSpeed*Math.abs(leftSpeed), 
                             rightSpeed*Math.abs(rightSpeed));
        }
        //Updates Screen with new Info
        teleOpScreen();
    }
    
    /**
     * Screen Functions
     */
    void teleOpScreen(){
        screen.println(DriverStationLCD.Line.kUser2, 1,"Easy Mode: "+isArcadeDrive);
        screen.println(DriverStationLCD.Line.kUser3, 1,
                "LM: "+(Math.floor(leftMotor.get()*100+5)/100)+
                "RM: "+(Math.floor(rightMotor.get()*100+5)/100));
        screen.updateLCD();
    }
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
