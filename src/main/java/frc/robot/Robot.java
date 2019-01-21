package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.util.SnailController;
import frc.subsystems.DriveTrain;


public class Robot extends TimedRobot {
  SnailController controller;
  DriveTrain drive;
  boolean ReverseDriveOn = false;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    controller = new SnailController(RobotMap.controllerPort);
    drive = new DriveTrain();
    
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
    double turnSpeed = controller.getTurnSpeed();
    double forwardSpeed = controller.getForwardSpeed();
    if(controller.getBButton()){
      if(ReverseDriveOn = false){
        ReverseDriveOn = true;
      }
      else{
        ReverseDriveOn = false;
      }
    }
    if(ReverseDriveOn){
      forwardSpeed = -1 * forwardSpeed;
      turnSpeed = -1 * turnSpeed;
    }
    drive.drive(forwardSpeed, turnSpeed);

  }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        
    }
}
