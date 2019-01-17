package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.subsystems.HatchIntake;

public class Robot extends TimedRobot {

    HatchIntake hatchIntake;
    XboxController Controller;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        hatchIntake = new HatchIntake();
        hatchIntake.reset();
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

        if(Controller.getXButton())
        {
            hatchIntake.pickupExtend();
        }

        if(Controller.getYButton())
        {
            hatchIntake.pickupRetract();
        }

        if(Controller.getAButton())
        {
            hatchIntake.ejectExtend();
        }

        if(Controller.getYButton())
        {
            hatchIntake.ejectRetract();
        }

        hatchIntake.hatchPivot(Controller.getTriggerAxis​(GenericHID.Hand kRight));

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
