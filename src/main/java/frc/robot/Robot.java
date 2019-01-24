package frc.robot;

import edu.wpi.first.wpilibj.*;

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
      
      if(Controller.getXButton() && !climb.FrontOn)
      {
        climb.frontForward();
      }
      else if(Controller.getXButton() && climb.FrontOn)
      {
        climb.frontReverse();
      }
      if(Controller.getYButton() && !climb.BackOn)
      {
        climb.backForward();
      }
      else if(Controller.getYButton() && climb.BackOn)
      {
        climb.backReverse();
      }
      if(Controller.getBButton())
      {
        climb.reset();
      }

      if(climb.FrontOn == true || climb.BackOn == true) {
        double climbDriveSpeed = (-1 * Controller.getY(GenericHID.Hand.kLeft));
        climb.climbDrive(climbDriveSpeed);
      }

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
