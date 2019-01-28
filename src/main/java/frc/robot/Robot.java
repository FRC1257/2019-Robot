package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystems.*;

public class Robot extends TimedRobot {
    Climb climb;
    XboxController Controller;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
    climb = new Climb();
    climb.reset();
    Controller = new XboxController(1);
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

      // pushes front up
      if(Controller.getXButton() && !climb.getFront()) {
        climb.frontForward();
      }

      // pushes front down
      else if(Controller.getXButton() && climb.getFront()) {
        climb.frontReverse();
      }

      // pushes back up
      if(Controller.getYButton() && !climb.getBack()) {
        climb.backForward();
      }

      // pushes back down
      else if(Controller.getYButton() && climb.getBack()) {
        climb.backReverse();
      }
      //alternates between phases of the climb
      if(Controller.getAButton() && climb.getState() == 1 && !climb.getBack() && !climb.getFront())
      {
      climb.phase1Climb();
      }
      else if(Controller.getAButton() && climb.getState() == 2 && climb.getBack() && climb.getFront())
      {
      climb.phase2Climb();
      }
      else if(Controller.getAButton() && climb.getState() == 3 && climb.getBack() && !climb.getFront())
      {
      climb.phase3Climb();
      }
      
      // resets robot
      if(Controller.getBButton()) {
        climb.reset();
      }

      double climbDriveSpeed = 0;

      // drives robot unless both of the solenoids are down
      if(climb.getFront() || climb.getBack()) {
        climbDriveSpeed = (-1 * Controller.getY(GenericHID.Hand.kLeft));
        climb.climbDrive(climbDriveSpeed);
      }

      SmartDashboard.putBoolean("FrontOn", climb.getFront());
      SmartDashboard.putBoolean("BackOn", climb.getBack());
      SmartDashboard.putNumber("climb motor speed", climbDriveSpeed); // puts states of the solenoids and the motor speed on smartdashboard
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
