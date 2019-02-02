package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import frc.subsystems.*;

public class Robot extends TimedRobot {

    HatchIntake hatchIntake;
    OI oi;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        hatchIntake = HatchIntake.getInstance();
        oi = OI.getInstance();

        hatchIntake.setConstantTuning();
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
        if(oi.getTogglePivot()) hatchIntake.togglePivot();

        if(!hatchIntake.getPIDRunning()) {
            hatchIntake.setPickup(oi.getHatchPickup());
            hatchIntake.setPivot(oi.getHatchPivot());

            // Only allow the hatch to eject if the hatch is not lowered and there is a hatch detected
            if(!hatchIntake.isLowered() && hatchIntake.getLimitSwitchHatch()) {
                hatchIntake.setEject(oi.getHatchEject());
            }
            else {
                hatchIntake.ejectRetract();
            }

            if(hatchIntake.getLimitSwitchPivot()) {
                hatchIntake.resetEncoder();
            }
        }

        hatchIntake.updatePositionState();
        hatchIntake.outputValues();
        hatchIntake.getConstantTuning();
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
