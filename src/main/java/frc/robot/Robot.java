package frc.robot;

import frc.subsystems.*;
import frc.util.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;;

public class Robot extends TimedRobot {

    DriveTrain drive;
    IntakeArm intakeArm;
    CargoIntake cargoIntake;
    HatchIntake hatchIntake;
    Climb climb;

    Gyro gyro;

    OI oi;

    NetworkTable limelightNetworkTable;

    @Override
    public void robotInit() {
        drive = DriveTrain.getInstance();
        intakeArm = IntakeArm.getInstance();
        cargoIntake = CargoIntake.getInstance();
        hatchIntake = HatchIntake.getInstance();
        climb = Climb.getInstance();

        gyro = Gyro.getInstance();

        oi = OI.getInstance();

        CameraServer.getInstance().startAutomaticCapture();
        limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
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
    }

    public void teleopFunctionality() {
        // Drive
        if (oi.getDriveReverse()) {
            drive.toggleReverse();
        }
        drive.drive(oi.getDriveForwardSpeed(), oi.getDriveTurnSpeed());

        // Vision
        if (limelightNetworkTable.getEntry("pipeline").getNumber(2).equals(2)) {
            limelightNetworkTable.getEntry("pipeline").setNumber(2);
        }

        // Intake Arm
        if (oi.getArmRaise()) {
            intakeArm.raiseArm();
        }
        if (oi.getArmLower()) {
            intakeArm.lowerArm();
        }
        intakeArm.setSpeed(oi.getArmSpeed());

        // Cargo Intake
        if (oi.getCargoShootButton()) {
            cargoIntake.shoot();
        } else if (oi.getCargoIntakeButton()) {
            cargoIntake.intake();
        } else {
            cargoIntake.constantIntake();
        }

        // Hatch Intake
        if (oi.getHatchRaise()) {
            hatchIntake.raisePivot();
        }
        if (oi.getHatchLower()) {
            hatchIntake.lowerPivot();
        }
        hatchIntake.setPickup(oi.getHatchPickup());
        hatchIntake.setPivot(oi.getHatchPivot());
        hatchIntake.setEject(oi.getHatchEject());

        // Climb
        if (oi.getClimbAdvance()) {
            climb.advanceClimb();
        }
        if (oi.getClimbReset()) {
            climb.reset();
        }
        if (oi.getClimbBackToggle()) {
            climb.toggleBack();
        }
        if (oi.getClimbFrontToggle()) {
            climb.toggleFront();
        }
        climb.climbDrive(oi.getClimbDriveSpeed());
        climb.outputValues();

        gyro.displayAngle();

        oi.updateControllers();
    }
}
