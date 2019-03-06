package frc.robot;

import frc.subsystems.*;
import frc.util.*;
import frc.util.snail_vision.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    SnailVision vision;
    double driveSpeed;
    double turnSpeed;
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

        intakeArm.setConstantTuning();
        cargoIntake.setConstantTuning();
        hatchIntake.setConstantTuning();

        vision = new SnailVision(true);
        RobotMap.initializeVision(vision);
        driveSpeed = 0;
        turnSpeed = 0;
        CameraServer.getInstance().startAutomaticCapture();
        limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
        setVisionConstantTuning();
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

        if (oi.getClimbBackToggle()) {
            climb.toggleBack();
        }
        if (oi.getClimbFrontToggle()) {
            climb.toggleFront();
        }

        // Vision recording measurements for area to distance
        if (oi.getRecordArea()) {
            vision.recordTargetArea();
        }
        if (oi.getSaveArea()) {
            vision.clearTargetArea();
        }
        if (oi.getPrintArea()) {
            vision.printTargetArea();
        }
        if (oi.getResetArea()) {
            vision.resetTargetArea();
        }

        getVisionConstantTuning();
    }

    public void teleopFunctionality() {
        driveSpeed = 0;
        turnSpeed = 0;

        // Vision
        visionFunctionality();

        // Drive
        driveSpeed += oi.getDriveForwardSpeed();
        turnSpeed += oi.getDriveTurnSpeed();
        drive.drive(driveSpeed, turnSpeed);

        if (oi.getDriveReverse())
            drive.toggleReverse();

        // Intake Arm
        if (oi.getArmRaise())
            intakeArm.raiseArm();
        if (oi.getArmLower())
            intakeArm.lowerArm();
        if (!intakeArm.getPIDRunning()) {
            intakeArm.setSpeed(oi.getArmSpeed());

            if (intakeArm.getLimitSwitch()) {
                intakeArm.resetEncoder();
            }
        }
        if (oi.getArmPIDBreak()) {
            intakeArm.breakPID();
        }
        intakeArm.updatePositionState();
        intakeArm.outputValues();

        // Cargo Intake
        if (oi.getCargoShootButton()) {
            cargoIntake.shoot();
        } else if (oi.getCargoIntakeButton()) {
            cargoIntake.intake();
        } else {
            cargoIntake.constantIntake();
        }

        // Hatch Intake
        if (oi.getHatchRaise())
            hatchIntake.raisePivot();
        if (oi.getHatchLower())
            hatchIntake.lowerPivot();
        if (!hatchIntake.getPIDRunning()) {
            hatchIntake.setPickup(oi.getHatchPickup());
            hatchIntake.setPivot(oi.getHatchPivot());

            // Only allow the hatch to eject if the hatch is not lowered and there is a
            // hatch detected
            // if(!hatchIntake.isLowered() && hatchIntake.getLimitSwitchHatch()) {
            hatchIntake.setEject(oi.getHatchEject());
            // }
            // else {
            // hatchIntake.ejectRetract();
            // }

            if (hatchIntake.getLimitSwitchPivotPressed()) {
                hatchIntake.resetEncoder();
            }
        }
        if (oi.getHatchPIDBreak()) {
            hatchIntake.breakPID();
        }
        hatchIntake.updatePositionState();
        hatchIntake.outputValues();

        // Climb
        if (oi.getClimbAdvance())
            climb.advanceClimb();
        if (oi.getClimbReset())
            climb.reset();
        climb.climbDrive(oi.getClimbDriveSpeed());
        climb.outputValues();

        gyro.displayAngle();

        oi.updateControllers();
    }

    public void visionFunctionality() {
        // vision.gyroFunctionality();
        vision.networkTableFunctionality(NetworkTableInstance.getDefault().getTable("limelight"));

        if (oi.getTurnCorrect() > 0 && oi.getTurnCorrect() < 0.9) {
            if (vision.currentPipeline.get(0) != 1) {
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 1); // Changes to 1
                                                                                                        // target to
                                                                                                        // expand vision
            }
            turnSpeed -= vision.angleCorrect();
        } else if (oi.getTurnCorrect() > 0.9) {
            if (vision.currentPipeline.get(0) != 0) {
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 0); // Dual Target
            }
            turnSpeed -= vision.angleCorrect();
        }

        if (oi.getTurnCorrectRelease()) {
            if (vision.currentPipeline.get(0) != 2) {
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 2); // Switches to
                                                                                                        // default
                                                                                                        // driver
                                                                                                        // pipeline
            }
        }

        if (oi.getAimbot() > 0) {
            if (vision.currentPipeline.get(0) != 1) {
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 1); // Changes to 1
                                                                                                        // target to
                                                                                                        // expand vision
            }
            turnSpeed += vision.angleCorrect();
            if (vision.instantaneousJerk > vision.JERK_COLLISION_THRESHOLD) {
                hatchIntake.pickupExtend();
            } else {
                hatchIntake.pickupRetract();
            }
        }

        if (oi.getAimbotRelease()) {
            if (vision.currentPipeline.get(0) != 2) {
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 2); // Switches to
                                                                                                        // default
                                                                                                        // driver
                                                                                                        // pipeline
            }
        }
        outputVisionValues();
    }

    public void setVisionConstantTuning() {
        SmartDashboard.putNumber("Vision Angle Correct P", vision.ANGLE_CORRECT_P);
        SmartDashboard.putNumber("Vision Angle Correct F", vision.ANGLE_CORRECT_F);
        SmartDashboard.putNumber("Vision Distance Correct P", vision.GET_IN_DISTANCE_P);
    }

    public void getVisionConstantTuning() {
        vision.ANGLE_CORRECT_P = SmartDashboard.getNumber("Vision Angle Correct P", vision.ANGLE_CORRECT_P);
        vision.ANGLE_CORRECT_F = SmartDashboard.getNumber("Vision Angle Correct F", vision.ANGLE_CORRECT_F);
        vision.GET_IN_DISTANCE_P = SmartDashboard.getNumber("Vision Distance Correct P", vision.GET_IN_DISTANCE_P);
    }

    public void outputVisionValues() {
        SmartDashboard.putNumber("Vision Jerk Value", vision.instantaneousJerk);
    }
}
