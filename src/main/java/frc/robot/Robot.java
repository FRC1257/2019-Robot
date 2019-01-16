package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.subsystems.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;

public class Robot extends TimedRobot {

    XboxController Controller;
    
    @Override
    public void robotInit() {
        Controller = new XboxController(0);
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
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

        if(Controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0.5){ //If left trigger pressed and a target on screen then turn to it
            DriveTrain.arcadeDrive(0, Vision.angleCorrect(table)); //Add when actual drivetrain exists
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
