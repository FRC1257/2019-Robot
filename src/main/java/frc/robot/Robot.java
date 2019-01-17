/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.util.SnailController;
import frc.subsystem.DriveTrain;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
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
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
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
