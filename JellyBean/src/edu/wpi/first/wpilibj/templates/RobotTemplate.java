/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;


/**3
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
        Victor Rightly;
        Victor Leftly;
        Joystick Eudado;
        Joystick Apa;
        DriverStationLCD Screen;
        MaxbotixUltrasonic Sonar;
        Kinect Dockes;
        RobotDrive Bob;
        Relay Shooter;
        Relay Loader;
        Relay Feeder;
       
       
        
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Rightly = new Victor(1,4);
        Leftly =new Victor(1,3);
        Eudado =new Joystick(1);
        Apa = new Joystick(2);
        Screen= DriverStationLCD.getInstance();
        Screen.updateLCD();       
        Sonar=new MaxbotixUltrasonic(7);     
        Dockes= Kinect.getInstance();
        Bob=new RobotDrive(Rightly,Leftly);
        Shooter =new Relay(3);
        Feeder =new Relay(2);
        Loader =new Relay(1);  
        
                
                
              
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
     
    }
    public void buttondrive(){
            
        if(Eudado.getRawButton(3)) {
            Leftly.set(.5);
        }        
        else if(Eudado.getRawButton(2))
            Leftly.set(-.5);       
        else{
           Leftly.set(0);
        }
            
        if(Apa.getRawButton(3)) {
            Rightly.set(.5);
        }
        else if(Apa.getRawButton(2))
            Rightly.set(-.5);          
        else{
            Rightly.set(0);          
        }           
            }
  public void teleopContinuous(){
   //RobotDrive();
    buttondrive();
    
 }            
 public void disabledInit(){
  

}    
   
public void disabledPeriodic(){
    
}       
public void disabledContinuous(){
    

}
public void teleopInit(){
       
}
public void RobotDrive(){
    
  //get joystick values 
  double x=Apa.getY();
  double y=-1*Eudado.getY();
  
  //So when going backwards it doesn't go forward, because we are squaring it
  if(x<0){
      x=x*x*-1;
  }
  else{
      x=x*x;
  }
  if(y<0){
      y=y*y*-1;
  }
  else{
      y=y*y;
  }
    //set the motors to the altered joystick values
  Leftly.set(x);
  Rightly.set(y);
  Screen.println(DriverStationLCD.Line.kUser2,1,"L:"+ x);
  Screen.println(DriverStationLCD.Line.kUser3,1, "R:" + y);
  Screen.println(DriverStationLCD.Line.kUser4,1,"JL:" + Apa.getY());
  Screen.println(DriverStationLCD.Line.kUser5,1,"JR:" + Eudado.getY());  
  Screen.println(DriverStationLCD.Line.kUser6,1,"SD" + SonarInches());
  Screen.updateLCD();
  
}
   public double SonarInches (){  
   return Sonar.GetVoltage()/(5/512);                
                  }
   public void Kinecttrack(){              
       double s=Dockes.getSkeleton().GetHandRight().getY();
       double t=Dockes.getSkeleton().GetHandRight().getX();
       double e=Dockes.getSkeleton().GetHandLeft().getY();
       double d=Dockes.getSkeleton().GetHandLeft().getX();
       Bob.arcadeDrive(s,t);
       
       
   }
   public void ShooterFunction(){
      if(Apa.getRawButton(1)) Shooter.set(Relay.Value.kOn);
      else Shooter.set(Relay.Value.kOff);
      if(Eudado.getRawButton(5)) Feeder.set(Relay.Value.kOn);
      else Feeder.set(Relay.Value.kOff);
      if(Apa.getRawButton(4)) Loader.set(Relay.Value.kOn);
      else Loader.set(Relay.Value.kOff);
      if(Eudado.getRawButton(4)) Loader.set(Relay.Value.kOff);
      
     
   }
}