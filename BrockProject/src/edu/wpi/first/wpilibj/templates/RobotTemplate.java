package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
public class RobotTemplate extends IterativeRobot {
         Victor right,left,larm,rarm; Jaguar ih;
         Relay olleh,hello; Joystick r,l,controler;    
         int driveMode,kinectMode,num,mun,time,p,fire;
         RobotDrive easydrive; DriverStationLCD TV;
         MaxbotixUltrasonic ranger; Kinect k;
         double distance,biggestVolt,countvolt,a,b,c,s,d,f,g,h,j,x,y,arml,armr,ML,MR,arm;
    DigitalInput limitdown = new DigitalInput(1);
    DigitalInput limitup = new DigitalInput(2);
    public void robotInit() {
       left = new Victor(1,1); right = new Victor(1,4);
       olleh = new Relay(1); hello = new Relay(2);//belt
       larm = new Victor(1,3); rarm = new Victor(1,2);
       ih = new Jaguar(1,5);//flywheel
       l = new Joystick(1);r = new Joystick(2);
       driveMode=0;
       easydrive=new RobotDrive(left,right); 
       TV = DriverStationLCD.getInstance();
       ranger =new MaxbotixUltrasonic(7);
       a = -1; b = 1;k =  Kinect.getInstance();
       p=2;fire=0;armr=-1; arml=1;
    }
    //flywheel
    public void flywheel(){
        if(l.getRawButton(1)){ih.set(Math.floor((((l.getZ()*-1)+1)/2)*100)/100);
        }else{ih.set(0);}
    }

        
    public void autonomousPeriodic(){clearscreen();}
    public void teleopPeriodic(){clearscreen();}
    //kinect
    public void Kinect(){s= k.getSkeleton().GetHandRight().getX()-(k.getSkeleton().GetShoulderRight().getX());
        d= k.getSkeleton().GetHandRight().getY()-(k.getSkeleton().GetShoulderRight().getY());
        f= k.getSkeleton().GetHandLeft().getX()-(k.getSkeleton().GetShoulderLeft().getX());
        g= k.getSkeleton().GetHandLeft().getY()-(k.getSkeleton().GetShoulderLeft().getY());}
    public void kinecttank(){Kinect();
       left.set(d);
       right.set(-g);
       if(f>=.25){olleh.set(Relay.Value.kOn);
             hello.set(Relay.Value.kOn);
             olleh.setDirection(Relay.Direction.kForward);
             hello.setDirection(Relay.Direction.kForward);
       }else if(f>=-.25){olleh.set(Relay.Value.kOn);
             hello.set(Relay.Value.kOn);
             olleh.setDirection(Relay.Direction.kReverse);
             hello.setDirection(Relay.Direction.kReverse);           
       }else{olleh.set(Relay.Value.kOff);
             hello.set(Relay.Value.kOff);}
    }
    //belt
    public void beltdrive(){
        olleh.free();
        hello.free();
        if(l.getRawButton(2)){olleh.setDirection(Relay.Direction.kReverse);
            hello.setDirection(Relay.Direction.kReverse);
            olleh.set(Relay.Value.kOn);
            hello.set(Relay.Value.kOn);}
        else if(l.getRawButton(3)){olleh.setDirection(Relay.Direction.kForward);
            hello.setDirection(Relay.Direction.kForward);
            olleh.set(Relay.Value.kOn);
            hello.set(Relay.Value.kOn);}
        else{olleh.set(Relay.Value.kOff);
        hello.set(Relay.Value.kOff);
        }
    }
    //arm
    public void arm(){arm=.3;
        if(l.getRawButton(6)){
            if (limitdown.get() == true){larm.set(arm);
            rarm.set(arm);}else{}
        }else if(l.getRawButton(7)){
            if (limitup.get() == true){larm.set(-arm);
            rarm.set(-arm);}else{}
        }else{larm.set(0);rarm.set(0);}
    }
    //math disply
    public void math(){MR = (Math.floor(right.get()*1000)*1)/10;
        ML = (Math.floor(left.get()*1000)*-1)/10;}
    //drive type
    public void ButtonDrive(){//x   = Math.floor((((r.getZ()*-1)+1)/2)*100)/100;
        //if(r.getRawButton(3))left.set( x * a);
        //else if(r.getRawButton(2))left.set(x*b);
        //else if(r.getRawButton(5))left.set(  x*a);
        //else if(r.getRawButton(4)&&r.getRawButton(1))left.set(  x*b);
        //else left.set(0);
        //if(r.getRawButton(3))right.set(  x*b);
        //else if(r.getRawButton(2))right.set( x* a);
        //else if(r.getRawButton(4))right.set(  x*b);
        //else if(r.getRawButton(5)&&r.getRawButton(1))right.set(  x*a);
        /*else right.set(0);*/}
    public void tankdrive(){left.set(l.getY());      
        right.set(-r.getY());}
    public void arcade(){if(r.getRawButton(4)) p=1;
        else if(l.getRawButton(5)&&p==1) p=2;   
        if(p==1) easydrive.arcadeDrive(l,true);
        else if(p==2) easydrive.arcadeDrive(r,true);}
    public void teleopContinuous(){beltdrive();
        //if(r.getRawButton(7))driveMode=2;
        /*else*/ if (r.getRawButton(6)) driveMode=0;
       else if (l.getRawButton(11)) driveMode=1;
        //else if (l.getRawButton(10))driveMode=3;
        //if(driveMode==2)ButtonDrive();
        /*else*/ if(driveMode==1)arcade();
        else if(driveMode==0)tankdrive();
        //else if(driveMode==3)kinecttank();
        if(l.getRawButton(1)){olleh.set(Relay.Value.kOn);
            hello.set(Relay.Value.kOn);
            hello.setDirection(Relay.Direction.kReverse);
            olleh.setDirection(Relay.Direction.kReverse);
        }else{olleh.set(Relay.Value.kOff);
            hello.set(Relay.Value.kOff);}
        getBiggestVoltage (((l.getZ()+1)/2*100+.5)/100);
        updateTeleopScreen(); 
    }
    //random stuff
    public void disabledInit(){driveMode=0;}
    public void disabledPeriodic(){}
    public void disabledContinuous(){}
    public void teleopInit(){}
    //disply
    public void belt(){
        if(l.getRawButton(2)||l.getRawButton(3))TV.println(DriverStationLCD.Line.kUser6, 1,"belt on"); 
    }
    public void InKinectMode(){
        //if(s<=.25||f>=-.25||f<=.25){
            //TV.println(DriverStationLCD.Line.kUser2, 1,"B:Kinect");
            //TV.println(DriverStationLCD.Line.kUser3, 1,"Left : "+ML+"%");
            //TV.println(DriverStationLCD.Line.kUser4, 1,"right: "+MR+"%");
        //}else if(s>=.25){
            //TV.println(DriverStationLCD.Line.kUser2, 1,"Firing");
            //ih.set(1);
            //olleh.setDirection(Relay.Direction.kForward);
            //hello.setDirection(Relay.Direction.kForward);
            //olleh.set(Relay.Value.kOn);
            //hello.set(Relay.Value.kOn);
        //}else if(f>=.25||f<=-.25){TV.println(DriverStationLCD.Line.kUser2, 1,"belt:"+f);}
    }
    public void normalscreen(){
        switch(driveMode){
            case 0:
                math();
                TV.println(DriverStationLCD.Line.kUser2, 1,"B:Tank Drive");
                TV.println(DriverStationLCD.Line.kUser3, 1,"Left : "+ML+"%");
                TV.println(DriverStationLCD.Line.kUser4, 1,"right: "+MR+"%");
                TV.println(DriverStationLCD.Line.kUser5, 1,inchesTofeet(distance)[0]+"ft "+inchesTofeet(distance)[1]+"in ");
                TV.println(DriverStationLCD.Line.kUser6, 1,"flywheel:"+Math.floor((((l.getZ()*-1)+1)/2)*1000)/10+"%");
                if(l.getRawButton(1))TV.println(DriverStationLCD.Line.kMain6, 1,"Firing"); 
                break;
            case 1:
                math();
                TV.println(DriverStationLCD.Line.kUser2, 1,"B:Arcade Drive");
                TV.println(DriverStationLCD.Line.kUser3, 1,"Left : "+ML+"%");
                TV.println(DriverStationLCD.Line.kUser4, 1,"right: "+MR+"%");
                TV.println(DriverStationLCD.Line.kUser5, 1,inchesTofeet(distance)[0]+"ft "+inchesTofeet(distance)[1]+"in ");
                TV.println(DriverStationLCD.Line.kUser6, 1,"flywheel:"+Math.floor((((l.getZ()*-1)+1)/2)*1000)/10+"%");
                if(l.getRawButton(1))TV.println(DriverStationLCD.Line.kMain6, 1,"Firing");
                break;
            /*case 2:
                math();            
                TV.println(DriverStationLCD.Line.kUser2, 1,"B:Button Drive");
                TV.println(DriverStationLCD.Line.kUser3, 1,"LM;"+ML+"% RM:"+MR+"%"); 
                TV.println(DriverStationLCD.Line.kUser4, 1,inchesTofeet(distance)[0]+"ft "+inchesTofeet(distance)[1]+"in ");  
                TV.println(DriverStationLCD.Line.kUser5, 1,"speed: "+Math.floor(-((r.getZ()*-1+1)/2)*-1000)/10+"%");
                TV.println(DriverStationLCD.Line.kUser6, 1,"flywheel:"+Math.floor((((l.getZ()*-1)+1)/2)*1000)/10+"%");
                if(l.getRawButton(1))TV.println(DriverStationLCD.Line.kMain6, 1,"Firing");
                break;
            case 3:
                break;*/
            default:
                TV.println(DriverStationLCD.Line.kUser2, 1,"ur robot is messed up");
        }
    }
    //update screen 
    void updateTeleopScreen(){
        if(l.getRawButton(2)); 
        //else if(driveMode==3) InKinectMode();        
        else normalscreen();
        DriverStationLCD.getInstance().updateLCD();
    }
    void autoModescreen(){
        TV.println(DriverStationLCD.Line.kUser3, 1,"Left motor"+ML+"%");
        TV.println(DriverStationLCD.Line.kUser4, 1,"right motor"+ MR+"%");
        TV.println(DriverStationLCD.Line.kUser5, 1,inchesTofeet(distance)[0]+"ft "+inchesTofeet(distance)[1]+"in ");
        DriverStationLCD.getInstance().updateLCD();    
    }
    void clearscreen(){
        TV.println(DriverStationLCD.Line.kUser2, 1,"                              ");  
        TV.println(DriverStationLCD.Line.kUser3, 1,"                             ");
        TV.println(DriverStationLCD.Line.kUser4, 1,"                            ");
        TV.println(DriverStationLCD.Line.kUser5, 1,"                           ");
        TV.println(DriverStationLCD.Line.kUser6, 1,"                          ");
        TV.println(DriverStationLCD.Line.kMain6, 1,"                         "); 
        DriverStationLCD.getInstance().updateLCD();     
    }
    //ultrasonic
    double voltsToInches(double volt){ return Math.floor(volt/(5.0/512.0)+.5);}
    double[] inchesTofeet(double inch){
        double in = inch%12;
        double ft = Math.floor(inch/12);
        double[] ftandn = new double[2];
        ftandn[0] = ft;
        ftandn[1] = in;
        return ftandn;
    }
    void getBiggestVoltage(double ammount){
        double volt = ranger.GetVoltage();
        countvolt++;
        if(biggestVolt<volt)biggestVolt=volt;
        if(countvolt>=ammount){
            distance=voltsToInches(biggestVolt);
            biggestVolt=0;
            countvolt=1;
        }
    }    
}