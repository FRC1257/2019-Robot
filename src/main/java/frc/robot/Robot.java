/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.modifiers.*;
import jaci.pathfinder.followers.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final DifferentialDrive m_robotDrive
      = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
  private final Joystick m_stick = new Joystick(0);
  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();

    Waypoint[] points = new Waypoint[] {
      new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
      new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
      new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
  };
  
  // Create the Trajectory Configuration
  //
  // Arguments:
  // Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
  // Sample Count:        SAMPLES_HIGH (100 000)
  //                      SAMPLES_LOW  (10 000)
  //                      SAMPLES_FAST (1 000)
  // Time Step:           0.05 Seconds
  // Max Velocity:        1.7 m/s
  // Max Acceleration:    2.0 m/s/s
  // Max Jerk:            60.0 m/s/s/s
  Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
  
  // Generate the trajectory
  Trajectory trajectory = Pathfinder.generate(points, config);

  // The distance between the left and right sides of the wheelbase is 0.6m
double wheelbase_width = 0.6;

// Create the Modifier Object
TankModifier modifier = new TankModifier(trajectory);

// Generate the Left and Right trajectories using the original trajectory
// as the centre
modifier.modify(wheelbase_width);

Trajectory left  = modifier.getLeftTrajectory();       // Get the Left Side
Trajectory right = modifier.getRightTrajectory();      // Get the Right Side

left.configureEncoder(encoder_position, 1000, wheel_diameter);
right.configureEncoder(encoder_position, 1000, wheel_diameter);

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
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
    m_robotDrive.arcadeDrive(m_stick.getY(), m_stick.getX());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
