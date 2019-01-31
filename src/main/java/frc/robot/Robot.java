package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.util.SnailController;
import frc.subsystems.DriveTrain;


public class Robot extends TimedRobot {
  SnailController controller;
  DriveTrain drive;
  OI oi;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    controller = new SnailController(RobotMap.DRIVE_CONTROLLER_PORT);
    drive = DriveTrain.getInstance();
    oi = OI.getInstance();
    
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
  }

    /**
     * This function is called once each time when the robot enters test mode.
     */
    @Override
    public void testInit() {
    }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    double xSpeed = 0.0;
    double zRotation = 0.0;

    if(controller.getBButtonPressed()) {
      if(!drive.reverse){
        drive.reverse = true;
      }
      else {
        drive.reverse = false;
      }
    }

    drive.drive(xSpeed, zRotation);

    if(controller.getAButton() && controller.getForwardSpeed() > 0) {
      if(controller.getTurnSpeed() > 0) {
        drive.flDrive.set(0.6);
        drive.flDrive.outputValues();
      } else {
        drive.frDrive.set(0.6);
        drive.flDrive.outputValues();
      }
    } 
  }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    
    }
}
