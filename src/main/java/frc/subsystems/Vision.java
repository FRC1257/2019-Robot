package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.drive.*;

public class Vision{

     public static double turnCorrect(NetworkTable table){

      NetworkTableEntry txE = table.getEntry("tx");
      double tx = txE.getDouble(0); //Gets the angle of how far away from the corsshair the object is

      double min_command = SmartDashboard.getNumber("min_command", RobotMap.TURN_CORRECT_MIN_COMMAND);  //Minimum motor input to move robot in case P can't do it 
      double Kp = SmartDashboard.getNumber("kP", RobotMap.TURN_CORRECT_KP); // for PID
      double heading_error = tx; 
      double steering_adjust = 0.0;

      if (tx > RobotMap.TURN_CORRECT_MIN_ANGLE){ // If the angle of the target is farther than 2 degrees do normal pid
        steering_adjust = Kp * heading_error - min_command;
      } 
      else if (tx < RobotMap.TURN_CORRECT_MIN_ANGLE) // If angle fo the target is less than 2 degrees, the motor will not be able to move the robot due to friction, so min_command is added to give it the minimum speed to move
      {
        steering_adjust = Kp * heading_error + min_command;
      }

     return(-steering_adjust); // return motor output
    }
    
    public static double getInDistance(NetworkTable table){ 
      double currentDistance = tableDistanceFromObject(table); //cameraHeight and cameraAngle are constants
      double distanceError = currentDistance - RobotMap.DESIRED_TARGET_DISTANCE;
      double driving_adjust = 0;
      if(distanceError > RobotMap.DISTANCE_CORRECT_ERROR){ // 3 inches of error space for PID
          driving_adjust = RobotMap.DISTANCE_CORRECT_KP * distanceError;
      }
      return(driving_adjust); // return motor output
    }

    public static double tableDistanceFromObject(NetworkTable table){ // Returns the closest distance the robot is to the target that is estimated using target area
      NetworkTableEntry taE = table.getEntry("ta");
      NetworkTableEntry tvE = table.getEntry("tv");
      double ta = taE.getDouble(0);
      double tv0 = tvE.getDouble(0); // Looks back 3 frames to see if the target was on the screen just to make sure that the limelight glitched and did not see the target for a split second
      double tv1 = tvE.getDouble(1);
      double tv2 = tvE.getDouble(2);
      double minDifference = 10000; // Just so that it finds a smaller value
      double minIndex = RobotMap.MEASUREMENT_AMOUNT - 1;
      if(tv0 == 1.0 || tv1 == 1.0 || tv2 == 1.0){ // If the target is on screen
          for(int i = 0; i < RobotMap.MEASUREMENT_AMOUNT - 1; i++){
              if(RobotMap.DISTANCE_TO_PERCENT[i] - ta < minDifference && RobotMap.DISTANCE_TO_PERCENT[i] - ta > 0){
                  minDifference = RobotMap.DISTANCE_TO_PERCENT[i] - ta;
                  minIndex = i;
              }
          }
          return(minIndex);
      }
      else{
        return(0);
      }
  }

  public static void shoot(NetworkTable table, DifferentialDrive DriveTrain){ //Sample code that the robot fired the projectile
    DriveTrain.arcadeDrive(getInDistance(table), turnCorrect(table)); // getInDistance and angleCorrect return values to correct the robot.
  }

  public static void changePipeline(NetworkTable table, double controllerInput, String hand){
    if(hand == "right"){
      if(controllerInput > 0){
        table.getEntry("pipeline").setNumber(0);
      }
    }
    else if(hand == "left"){
      if(controllerInput > 0 && controllerInput < 0.9){
        table.getEntry("pipeline").setNumber(1);
      }
      else if (controllerInput >= 0.9){
        table.getEntry("pipeline").setNumber(0);
      }
    }
  }
  
}