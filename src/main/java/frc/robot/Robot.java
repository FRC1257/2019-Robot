package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;

import frc.subsystems.*;

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
        hatchState = False // False means it is not down

        //Pickup solenoids will be down while X button is pressed
        if(Controller.getXButtonPressed())
        {
            hatchIntake.pickupExtend();
        }

        if(Controller.getXButtonReleased())
        {
            hatchIntake.pickupRetract();
        }

        //Eject solenoids will be down while Y button is pressed
        if(Controller.getYButton())
        {
            hatchIntake.ejectExtend();
        }

        if(Controller.getYButtonReleased())
        {
            hatchIntake.ejectRetract();
        }

        //A button will toggle the hatch pivot up and down
        if (Controller.getAButton())
        {
            if (hatchState == False)
            {
                hatchPivotPID.setReference(1, ControlType.kPosition); // value incorrect
            }
            else
            {
                hatchPivotPID.setReference(2, ControlType.kPosition); // value incorrect
            }
        }

        //the right trigger will move hatch pivot up and down manually
        hatchIntake.hatchPivot(Controller.getTriggerAxis​(GenericHID.Hand kRight));

        // hatch sequence will automatically occur when limit switch is touched
        automaticIntake()

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
