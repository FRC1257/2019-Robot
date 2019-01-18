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

     return(-steering_adjust);
    }
}