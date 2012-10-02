/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.camera.AxisCamera;
//import edu.wpi.first.wpilibj.image.*;
//import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import java.lang.Math;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    Victor leftM, rightM;//Declare Drive SpeedCtrls
    boolean isArcadeDrive; // Declares Drive Mode
    Joystick leftS,rightS;//Declare Joysticks objects
    double leftY,rightY;
    RobotDrive roboDR;//Declare RobotDrive Logic Object
    Jaguar shooter; Relay leftBelt,rightBelt;//Shooter-related SpeedCtrls
    //SpeedCtrls, limit switches, and variables for Horn
    Victor leftHorn,rightHorn; boolean hornIsUp;
    double hornSpeedUp, hornSpeedDown;
    DigitalInput limit1,limit2;
    DriverStationLCD screen;// Declare DriverStationLCD
    /*MaxbotixUltrasonic ranger; //Declares rangerFinder
    double distance,biggestVolt,countVolt;*/
    Kinect kinect; boolean isKinect;
    Timer time; //boolean inPosition;//autonomous related objects
    
    static int printSec,startSec;
    int disabledPeriodicLoops;
    
    /** Initialization Functions*/
    public void robotInit() {
        //sets Drive motors to channel and port
        leftM= new Victor(1,4);
        rightM = new Victor(1,1);
        //Shooter-realated
        shooter=new Jaguar(1,5);
        leftBelt=new Relay(1,1);
        rightBelt=new Relay(1,2);
        //Horn-Related
        leftHorn=new Victor(1,3);
        rightHorn=new Victor(1,2);
        //Sets JoySticks and RobotDrive
        leftS=new Joystick(1);
        rightS=new Joystick(2);
        roboDR=new RobotDrive(leftM,rightM);
        //limitSwitches for Horn Movement
        limit1 = new DigitalInput(1);
        limit2 = new DigitalInput(2);
        hornSpeedUp=.28; hornSpeedDown=.2;
        //sets Screen
        screen=DriverStationLCD.getInstance();
        screen.updateLCD();
        //rangeFinder set to Analog port
        /*ranger= new MaxbotixUltrasonic(7);
        countVolt=1;  biggestVolt=0;*/
        //Kinect
        kinect=Kinect.getInstance();
        isKinect=false;
        time=new Timer();//Timer
        
        disabledPeriodicLoops = 0;
    }
    public void disabledInit(){
        disabledPeriodicLoops = 0; // Reset the loop counter for disabled mode
        startSec = (int)(Timer.getUsClock() / 1000000.0);
        printSec = startSec + 1;
        
        time.stop();time.reset();
        leftBelt.set(Relay.Value.kOff);
        rightBelt.set(Relay.Value.kOff); 
        hornIsUp=true;
        /*countVolt=1;
        biggestVolt=0;
        inPosition=false;*/
        clearScreen();
    }
    public void autonomousInit(){
        time.start();
    }
    public void teleopInit(){}
    
    /**
     * Continuous Functions
     */
    public void disabledContinuous()
    {
        Timer.delay(0.005);
    }
    public void autonomousContinuous() {
        shooter.set(1);
        //setRangeToBigVolt(50);
        if(isKinect)hybridMode();
        else autoMode();
        autoModeScreen();
        Timer.delay(.005);
    }
    public void hybridMode(){
        //shooterRelated
        double y=((kinect.getSkeleton().GetHandLeft().getY())
                -(kinect.getSkeleton().GetShoulderLeft().getY()));
        double x=((kinect.getSkeleton().GetHandLeft().getX())
                -(kinect.getSkeleton().GetShoulderLeft().getX()));
        if(y>Math.abs(x)+.1){
            leftBelt.set(Relay.Value.kOn);
            rightBelt.set(Relay.Value.kOn);
            leftBelt.setDirection(Relay.Direction.kReverse);
            rightBelt.setDirection(Relay.Direction.kReverse);   
            //System.out.println("Up");
        }else if(y<-Math.abs(x)-.1){
            leftBelt.set(Relay.Value.kOn);
            rightBelt.set(Relay.Value.kOn);
            leftBelt.setDirection(Relay.Direction.kForward);
            rightBelt.setDirection(Relay.Direction.kForward);
            //System.out.println("Down");
        }else{
            leftBelt.set(Relay.Value.kOff);
            rightBelt.set(Relay.Value.kOff); 
            //System.out.println("Off");
        }
        //driveRelated
        double rightY=((kinect.getSkeleton().GetHandRight().getY())
                -(kinect.getSkeleton().GetShoulderRight().getY()))*1.5;
        double rightX=((kinect.getSkeleton().GetHandRight().getX())
                -(kinect.getSkeleton().GetShoulderRight().getX()))*1.5;
        roboDR.arcadeDrive(rightY,rightX,true);
    }
    public void autoMode(){
        //TestCode1
        /*double speed=-.45;
        double marginOfError=3.6;
        if(time.get()>5&&time.get()<15){
            if(distance>144+marginOfError||!inPosition){      
                roboDR.tankDrive(speed,speed);
                inPosition=false;
            }else if (distance<144-marginOfError||!inPosition){  
                roboDR.tankDrive(-speed,-speed);
                inPosition=false;
            }else if(time.get()>8){ 
                inPosition=true;
                roboDR.tankDrive(0,0);
                leftBelt.set(Relay.Value.kOn);
                rightBelt.set(Relay.Value.kOn);
                leftBelt.setDirection(Relay.Direction.kReverse);
                rightBelt.setDirection(Relay.Direction.kReverse);   
            }else{
                roboDR.tankDrive(0,0);
                leftBelt.set(Relay.Value.kOff);
                rightBelt.set(Relay.Value.kOff);
            }
        }*/
        
        //Actual
        if(time.get()>2&&time.get()<15){
            leftBelt.set(Relay.Value.kOn);
            rightBelt.set(Relay.Value.kOn);
            leftBelt.setDirection(Relay.Direction.kReverse);
            rightBelt.setDirection(Relay.Direction.kReverse);
        }else{
            leftBelt.set(Relay.Value.kOff);
            rightBelt.set(Relay.Value.kOff);
        }
    }
    
    public void teleopContinuous(){
        if (isArcadeDrive)  
             roboDR.arcadeDrive(rightS.getY(),rightS.getX(),true);
        else{
            leftY=leftS.getY();   rightY=rightS.getY();
            roboDR.tankDrive(leftY*Math.abs(leftY),rightY*Math.abs(rightY));
            //roboDR.tankDrive(leftS,rightS);
        }
        //setRangeToBigVolt(50);
        
        //Shooter Related 
        if (rightS.getRawButton(1)) shooter.set(1);
        else                        shooter.set(-(leftS.getZ()-1)/2);
        //BeltFeed Related
        if(rightS.getRawButton(2)){
            leftBelt.set(Relay.Value.kOn);
            rightBelt.set(Relay.Value.kOn);
            leftBelt.setDirection(Relay.Direction.kForward);
            rightBelt.setDirection(Relay.Direction.kForward);
        }else if(rightS.getRawButton(3) || leftS.getRawButton(1)){
            leftBelt.set(Relay.Value.kOn);
            rightBelt.set(Relay.Value.kOn);
            leftBelt.setDirection(Relay.Direction.kReverse);
            rightBelt.setDirection(Relay.Direction.kReverse);           
        }else{
            leftBelt.set(Relay.Value.kOff);
            rightBelt.set(Relay.Value.kOff); 
        }
        
        //HornMotor
        if(!hornIsUp&&limit1.get()){
            leftHorn.set(hornSpeedDown);
            rightHorn.set(hornSpeedDown);
        }else if(hornIsUp&&limit2.get()){
            leftHorn.set(-hornSpeedUp);
            rightHorn.set(-hornSpeedUp);
        /*if      ((leftS.getRawButton(3)||rightS.getRawButton(6))&&limit2.get()){
            leftHorn.set(-hornSpeedUp);
            rightHorn.set(-hornSpeedUp);
        }else if ((leftS.getRawButton(2)||rightS.getRawButton(7))&&limit1.get()){
            leftHorn.set(hornSpeedDown);
            rightHorn.set(hornSpeedDown);*/
        }else{
            leftHorn.set(0);
            rightHorn.set(0);
        }
        teleOpScreen();
        Timer.delay(.005);
    }
    
    /** Periodic Functions*/
    public void disabledPeriodic(){
        // feed the user watchdog at every period when disabled
        Watchdog.getInstance().feed();
        // increment the number of disabled periodic loops completed
        disabledPeriodicLoops++;
        // while disabled, printout the duration of current disabled mode in seconds
        if ((Timer.getUsClock() / 1000000.0) > printSec) {
            System.out.println("Disabled seconds: " + (printSec - startSec));
            printSec++;
        }
    }
    public void autonomousPeriodic() {
        clearScreen();
    }
    public void teleopPeriodic() {
        //HornMotor
        if      (leftS.getRawButton(3)||rightS.getRawButton(6)) hornIsUp=true;
        else if (leftS.getRawButton(2)||rightS.getRawButton(7)) hornIsUp=false;
        
        //drive
        if     (rightS.getRawButton(11)) isArcadeDrive=false;
        else if(rightS.getRawButton(10)) isArcadeDrive=true;
        
        clearScreen();
    }
    
    /** Screen Functions*/
    void teleOpScreen(){
        if(isArcadeDrive)
             screen.println(DriverStationLCD.Line.kUser2, 1,"Drive: Arcade");
        else screen.println(DriverStationLCD.Line.kUser2, 1,"Drive: Tank");
        screen.println(DriverStationLCD.Line.kUser3, 1,
            +(-Math.floor(leftM.get()*100)/100)+
            " "+(Math.floor(rightM.get()*100)/100));
        /*screen.println(DriverStationLCD.Line.kUser4, 1,
            inchesToFeet(distance)[0]+"ft "+inchesToFeet(distance)[1]+"in");*/
        screen.println(DriverStationLCD.Line.kUser4,1,
            "FlyWheel: "+(Math.floor(shooter.get()*100))+"%");
        screen.updateLCD();
    }
    void autoModeScreen(){
        
        screen.println(DriverStationLCD.Line.kUser2,1,
            "FlyWheel: "+(Math.floor(shooter.get()*100))+"%");
        /*screen.println(DriverStationLCD.Line.kUser3, 1,
            inchesToFeet(distance)[0]+"ft "+inchesToFeet(distance)[1]+"in");*/
        screen.updateLCD();
    }
    void clearScreen(){
        screen.println(DriverStationLCD.Line.kUser2, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser3, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser4, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser5, 1,"                     ");
        screen.println(DriverStationLCD.Line.kUser6, 1,"                     ");
        screen.updateLCD();
    }
    
    /** RangeFinder Functions*/
    /*double voltToInches(double volt){
        return Math.floor(volt/(5.0/512.0)+.5);
    }
    double[] inchesToFeet(double inch){ //Wiring Matt's Contribution
        double in=inch%12, ft=Math.floor(inch/12);
        double[] ftandn=new double[2];
        ftandn[0] = ft;     ftandn[1] = in;
        return ftandn;
    }
    void setRangeToBigVolt(double ammount){
        double volt=ranger.GetVoltage(); 
        countVolt++;
        if(biggestVolt<volt)biggestVolt=volt;
        if(countVolt>=ammount){
            distance=voltToInches(biggestVolt);
            biggestVolt=0; countVolt=1;
        }
    }*/
}
