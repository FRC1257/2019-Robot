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
      boolean frontState = climb.getFront();
      boolean backState  = climb.getBack();//states of the solenoids
      if(Controller.getXButton() && !frontState)//pushes front up
      {
        climb.frontForward();
      }
      else if(Controller.getXButton() && frontState)//pushes front down
      {
        climb.frontReverse();
      }
      if(Controller.getYButton() && !backState)//pushes back up
      {
        climb.backForward();
      }
      else if(Controller.getYButton() && backState)//pushes back down
      {
        climb.backReverse();
      }
      if(Controller.getBButton())//resets robot
      {
        climb.reset();
      }
      double climbDriveSpeed = 0;
      if(frontState || backState) { //drives robot unless both of the solenoids are down
        climbDriveSpeed = (-1 * Controller.getY(GenericHID.Hand.kLeft));
        climb.climbDrive(climbDriveSpeed);
      }
      SmartDashboard.putBoolean("FrontOn", frontState);
      SmartDashboard.putBoolean("BackOn", backState);
      SmartDashboard.putNumber("climb motor speed", climbDriveSpeed);//puts states of the solenoids and the motor speed on smartdashboard

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
