package frc.subsystems;

import frc.robot.RobotMap;

import frc.util.*;

import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


    public class Turning { 
        
        private static Turning instance = null;

        private boolean pidActive;
        private double lastTime;
        private double motorSpeed;

        Gyro gyro;
        SynchronousPIDF pid;
        DriveTrain driveTrain;
       
        
        private Turning() { 
        
            pidActive = false;
            lastTime = -1;
            motorSpeed = 0;

            driveTrain = DriveTrain.getInstance();
            gyro = Gyro.getInstance();
            pid = new SynchronousPIDF(RobotMap.TURN_PIDF[0], RobotMap.TURN_PIDF[1], RobotMap.TURN_PIDF[2], RobotMap.TURN_PIDF[3]);
        
   
        }

        // Singleton
        public static Turning getInstance() {
            if (instance == null) {
                instance = new Turning();
            }
            return instance;
        }
    

        public void TurnLeft() {
             // Only runs the first time
             if(!pidActive) {
                gyro.resetAngle();
                pid.reset();
        
                pid.setSetpoint(90);
                lastTime = Timer.getFPGATimestamp();

                pidActive = true;
            }
            else {
            // End the PID
                pidActive = false;
            }
         
            // Do the PID while the x button is first pressed
            motorSpeed = pid.calculate(gyro.getAngle(), Timer.getFPGATimestamp() - lastTime);
            lastTime = Timer.getFPGATimestamp();
            
            driveTrain.drive(0, motorSpeed);

        }

        public void TurnRight() {
            // Only runs the first time
            if(!pidActive) {
               gyro.resetAngle();
               pid.reset();
       
               pid.setSetpoint(90);
               lastTime = Timer.getFPGATimestamp();

               pidActive = true;
           }
           else {
            // End the PID
                pidActive = false;
            }
  
           // Do the PID while the x button is first pressed
           motorSpeed = pid.calculate(gyro.getAngle(), Timer.getFPGATimestamp() - lastTime);
           lastTime = Timer.getFPGATimestamp();
           
           driveTrain.drive(0, motorSpeed);

       }


        public void setConstantTuning() {
            SmartDashboard.putNumber("Turn P", RobotMap.TURN_PIDF[0]);
            SmartDashboard.putNumber("Turn I", RobotMap.TURN_PIDF[1]);
            SmartDashboard.putNumber("Turn D", RobotMap.TURN_PIDF[2]);
            SmartDashboard.putNumber("Turn F", RobotMap.TURN_PIDF[3]);
        
        }

        // Put in key & default value
        public void displayValues() {
            SmartDashboard.putNumber("Angle", gyro.getAngle());
        }

        // Update constants from Smart Dashboard
        public void updateConstantTuning() {
            if(RobotMap.TURN_PIDF[0] != SmartDashboard.getNumber("Turn P", RobotMap.TURN_PIDF[0])) { 
                RobotMap.TURN_PIDF[0] = SmartDashboard.getNumber("Turn P", RobotMap.TURN_PIDF[0]);
            }
        
            if(RobotMap.TURN_PIDF[1] != SmartDashboard.getNumber("Turn I", RobotMap.TURN_PIDF[1])) { 
                RobotMap.TURN_PIDF[1] = SmartDashboard.getNumber("Turn I", RobotMap.TURN_PIDF[1]);   
            }

            if(RobotMap.TURN_PIDF[2] != SmartDashboard.getNumber("Turn D", RobotMap.TURN_PIDF[2])) { 
                RobotMap.TURN_PIDF[2] = SmartDashboard.getNumber("Turn D", RobotMap.TURN_PIDF[2]);   
            }

            if(RobotMap.TURN_PIDF[3] != SmartDashboard.getNumber("Turn F", RobotMap.TURN_PIDF[3])) {
                RobotMap.TURN_PIDF[3] = SmartDashboard.getNumber("Turn F", RobotMap.TURN_PIDF[3]);   
            }

            pid.setPID(RobotMap.TURN_PIDF[0], RobotMap.TURN_PIDF[1], RobotMap.TURN_PIDF[2], RobotMap.TURN_PIDF[3]);
        }

}
    