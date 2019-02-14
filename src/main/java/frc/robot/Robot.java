package frc.robot;


import frc.subsystems.*;
import frc.util.*;
import edu.wpi.first.wpilibj.*;

public class Robot extends TimedRobot {

    DriveTrain drive;
    IntakeArm intakeArm;
    CargoIntake cargoIntake;
    HatchIntake hatchIntake;
    Climb climb;
    Turning turning;
    
    Gyro gyro;
    
    OI oi;
    
    @Override
    public void robotInit() {
        drive = DriveTrain.getInstance();
        intakeArm = IntakeArm.getInstance();
        cargoIntake = CargoIntake.getInstance();
        hatchIntake = HatchIntake.getInstance();
        climb = Climb.getInstance();
        turning = Turning.getInstance();

        gyro = Gyro.getInstance();

        oi = OI.getInstance();

        intakeArm.setConstantTuning();
        cargoIntake.setConstantTuning();
        hatchIntake.setConstantTuning();
        turning.setConstantTuning();
    }

    @Override
    public void autonomousInit() {
        
    }

    @Override
    public void autonomousPeriodic() {
        teleopFunctionality();
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        teleopFunctionality();
    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
        teleopFunctionality();

        // Test Code
        intakeArm.getConstantTuning();
        cargoIntake.getConstantTuning();
        hatchIntake.getConstantTuning();
        
        if(oi.getClimbBackToggle()) climb.toggleBack();
        if(oi.getClimbFrontToggle()) climb.toggleFront();
    }

    public void teleopFunctionality() {
        // Drive
        drive.drive(oi.getDriveForwardSpeed(), oi.getDriveTurnSpeed());
    
        
        // Intake Arm
        if(oi.getArmRaise()) intakeArm.raiseArm();
        if(oi.getArmLower()) intakeArm.lowerArm();
        if(!intakeArm.getPIDRunning()) {
            intakeArm.setSpeed(oi.getArmSpeed());
            
            if(intakeArm.getLimitSwitch()) {
                intakeArm.resetEncoder();
            }
        }
        intakeArm.updatePositionState();
        intakeArm.outputValues();
        
        
        // Cargo Intake
        // Constantly intake unless shooting
        if(oi.getCargoShootButton()) {
            cargoIntake.shoot();
        }
        else if(oi.getCargoIntakeButton()) {
            cargoIntake.intake();
        }
        else {
            cargoIntake.stop();
        }

        
        // Hatch Intake
        if(oi.getHatchPivotToggle()) hatchIntake.togglePivot();
        if(!hatchIntake.getPIDRunning()) {
            hatchIntake.setPickup(oi.getHatchPickup());
            hatchIntake.setPivot(oi.getHatchPivot());

            // Only allow the hatch to eject if the hatch is not lowered and there is a hatch detected
            // if(!hatchIntake.isLowered() && hatchIntake.getLimitSwitchHatch()) {
                hatchIntake.setEject(oi.getHatchEject());
            // }
            // else {
            //     hatchIntake.ejectRetract();
            // }

            if(hatchIntake.getLimitSwitchPivot()) {
                hatchIntake.resetEncoder();
            }
        }
        hatchIntake.updatePositionState();
        hatchIntake.outputValues();

        
        // Climb
        if(oi.getClimbAdvance()) climb.advanceClimb();
        if(oi.getClimbReset()) climb.reset();
        climb.climbDrive(oi.getClimbDriveSpeed());
        climb.outputValues();
        
        gyro.displayAngle();

        // Turning in place
        if(oi.getTurnLeft()) turning.TurnLeft();
        if(oi.getTurnRight()) turning.TurnRight();

        turning.displayValues();
        turning.updateConstantTuning();
        



    }
}
