package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.subsystems.*;

public class Robot extends TimedRobot {
    Climb climb;
    XboxController Controller;
    OI oi;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
      climb = new Climb();
      climb.reset();
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

      // For testing purposes only
      if(oi.getFrontChanger() && !climb.getFront()) {// pushes front up
        climb.frontForward();
      }
      else if(oi.getFrontChanger() && climb.getFront()) {// pushes front down
        climb.frontReverse();
      }
      if(oi.getBackChanger() && !climb.getBack()) {// pushes back up
        climb.backForward();
      }
      else if(oi.getBackChanger() && climb.getBack()) {// pushes back down
        climb.backReverse();
      }
      // alternates between phases of the climb
      if(oi.getClimbPhaser() && climb.getState() == 1 && !climb.getBack() && !climb.getFront()) {
      climb.phase1Climb();
      }
      else if(oi.getClimbPhaser() && climb.getState() == 2 && climb.getBack() && climb.getFront()) {
      climb.phase2Climb();
      }
      else if(oi.getClimbPhaser() && climb.getState() == 3 && climb.getBack() && !climb.getFront()) {
      climb.phase3Climb();
      }    
      if(Controller.getBButtonPressed()) {// resets robot
        climb.reset();
      }

      double CLIMB_DRIVE_SPEED = 0;

      // drives robot unless both of the solenoids are down
      if(climb.getFront() || climb.getBack()) {
        CLIMB_DRIVE_SPEED = (-1 * oi.getClimbMotorSpeed());
        climb.climbDrive(CLIMB_DRIVE_SPEED);
      }

      climb.smartDashboardClimb();
    }

    /**
     * This function is called once each time when the robot enters test mode.
     */
    @Override
    public void testInit() {

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        
    }
}
