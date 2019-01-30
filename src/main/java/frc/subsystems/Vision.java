package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

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
      // double KpDistance = RobotMap.DISTANCE_CORRECT_KP; // For p control
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
      double tv = tvE.getDouble(0);
      double minDifference = 10000; // Just so that it finds a smaller value
      double minIndex = RobotMap.MEASUREMENT_AMOUNT - 1;
      if(tv == 1.0){ // If the target is on screen
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

}