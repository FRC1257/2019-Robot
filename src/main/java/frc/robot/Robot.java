package frc.robot;

import frc.subsystems.*;
import edu.wpi.first.wpilibj.*;

public class Robot extends TimedRobot {
    
    IntakeArm intakeArm;
    CargoIntake cargoIntake;
    HatchIntake hatchIntake;
    
    OI oi;

    @Override
    public void robotInit() {
        intakeArm = IntakeArm.getInstance();
        cargoIntake = CargoIntake.getInstance();
        hatchIntake = HatchIntake.getInstance();
        oi = OI.getInstance();

        intakeArm.setConstantTuning();
        cargoIntake.setConstantTuning();
        hatchIntake.setConstantTuning();
    }

    @Override
    public void autonomousInit() {
        
    }

    @Override
    public void autonomousPeriodic() {
        
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        // Intake Arm
        if(oi.getArmRaise()) intakeArm.raiseArm();
        if(oi.getArmLower()) intakeArm.lowerArm();
        if(!intakeArm.getPIDRunning()) {
            intakeArm.setSpeed(oi.getArmSpeed());
            if(intakeArm.getLimitSwitch()) intakeArm.resetEncoder();
        }
        intakeArm.updatePositionState();
        intakeArm.outputValues();
        intakeArm.getConstantTuning();
        
        
        // Cargo Intake
        // Constantly intake unless shooting
        if(oi.getCargoShootButton()) {
            cargoIntake.shoot();
        }
        else {
            cargoIntake.intake();
        }
        cargoIntake.getConstantTuning();

        
        // Hatch Intake
        if(oi.getHatchPivotToggle()) hatchIntake.togglePivot();
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

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
        
        
    }
}
