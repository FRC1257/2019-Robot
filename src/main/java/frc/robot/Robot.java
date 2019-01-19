package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;

import frc.subsystems.*;

public class Robot extends TimedRobot {

    HatchIntake hatchIntake;
    XboxController operatorController;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        hatchIntake = new HatchIntake();

        operatorController = new XboxController(1);
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
        // Hatch Intake
        if(operatorController.getXButton()) hatchIntake.togglePivot();

        if(!hatchIntake.getPIDRunning()) {
            hatchIntake.setPickup(operatorController.getAButton());

            // Only allow the hatch to eject if the hatch is not lowered
            if(!hatchIntake.getLimitSwitchHatch()) {
                hatchIntake.setEject(operatorController.getBButton());
            }
            else {
                hatchIntake.ejectRetract();
            }

            hatchIntake.setPivot(operatorController.getY(Hand.kLeft));

            if(hatchIntake.getLimitSwitchPivot()) {
                hatchIntake.resetEncoder();
            }
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
