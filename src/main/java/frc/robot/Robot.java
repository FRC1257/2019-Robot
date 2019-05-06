package frc.robot;

import frc.subsystems.*;
import frc.util.*;
import frc.util.snail_vision.*;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
    // Climb climb;
    PowerDistributionPanel pdp;

    // Gyro gyro;

    OI oi;

    SnailVision vision;
    double driveSpeed;
    double turnSpeed;
    NetworkTable limelightNetworkTable;

    Target target;
    double[] areaToPercent;

    @Override
    public void robotInit() {
        drive = DriveTrain.getInstance();
        intakeArm = IntakeArm.getInstance();
        cargoIntake = CargoIntake.getInstance();
        hatchIntake = HatchIntake.getInstance();
        // climb = Climb.getInstance();

        // gyro = Gyro.getInstance();

        oi = OI.getInstance();

        areaToPercent = new double[] {5,3,2};
        target = new Target(5,48,100, areaToPercent); // height, desired distance, number of entrys, array of percent

        intakeArm.setConstantTuning();
        cargoIntake.setConstantTuning();

        vision = new SnailVision(true);
        RobotMap.initializeVision(vision);
        driveSpeed = 0;
        turnSpeed = 0;
        CameraServer.getInstance().startAutomaticCapture(0);
        // CameraServer.getInstance().startAutomaticCapture(1);

        limelightNetworkTable = NetworkTableInstance.getDefault().getTable("limelight");
        setVisionConstantTuning();

        pdp = new PowerDistributionPanel();
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
        drive.getConstantTuning();
        intakeArm.getConstantTuning();
        cargoIntake.getConstantTuning();
        hatchIntake.getConstantTuning();

        // if (oi.getClimbBackToggle()) {
        //     climb.toggleBack();
        // }
        // if (oi.getClimbFrontToggle()) {
        //     climb.toggleFront();
        // }

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
        vision.networkTableFunctionality(NetworkTableInstance.getDefault().getTable("limelight"));
        visionFunctionality();
        driveSpeed += oi.getDriveForwardSpeed();
        turnSpeed += oi.getDriveTurnSpeed();

        if(oi.driveController.getTriggerAxis(GenericHID.Hand.kRight) > 0.1){
            turnSpeed += vision.angleCorrect();
            System.out.println(vision.angleCorrect());
        }
        if(oi.driveController.getTriggerAxis(GenericHID.Hand.kLeft) > 0.1){
            turnSpeed += vision.angleCorrect();
            driveSpeed += vision.getInDistance(target);
        }

        // Drive
        if (oi.getDriveReverse()) {
            drive.toggleReverse();
        }
        if (oi.getDriveTurnLeft()) {
            drive.turnLeft();
        }
        else if (oi.getDriveTurnRight()) {
            drive.turnRight();
        }
        else {
            drive.resetPID();
            drive.drive(driveSpeed, turnSpeed);
        }
        // drive.outputValues();
        drive.drive(driveSpeed, turnSpeed);

        // Intake Arm
        if (oi.getArmRaise()) {
            intakeArm.raiseArm();
        }
        if (oi.getArmLower()) {
            intakeArm.lowerArm();
        }
        intakeArm.setSpeed(oi.getArmSpeed());
        intakeArm.update();
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

        if (oi.getHatchEject()) {
            hatchIntake.eject();
        } else if (oi.getHatchPickup()) {
            hatchIntake.intake();
        } else {
            hatchIntake.stop();
        }

        // Climb
        // if (oi.getClimbAdvance()) {
        //     climb.advanceClimb();
        // }
        // if(oi.getClimbBackward()) {
        //     climb.backClimb();
        // }
        // if (oi.getYeetThing()) {
        //     climb.advanceSecondaryClimb();
        // }
        // if (oi.getClimbReset()) {
        //     climb.reset();
        // }
        // climb.climbDrive(oi.getClimbDriveSpeed());
        // climb.outputValues();

        // gyro.displayAngle();
// 
        oi.updateControllers();

        SmartDashboard.putNumber("PDP Temperature", pdp.getTemperature());
        SmartDashboard.putData(pdp);
    }

    public void visionFunctionality() {
        // vision.gyroFunctionality();

        // if (oi.getTurnCorrect() > 0 && oi.getTurnCorrect() < 0.9) {
        //     if (vision.currentPipeline.get(0) != 1){
        //         SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 1); // Changes to 1 target to expand vision
        //     }
        //     turnSpeed += vision.angleCorrect();
        // }
        // else 
        if (oi.getTurnCorrect() > 0) {
            // if (vision.currentPipeline.get(0) != 0) {
            //     NetworkTableHelper.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 0); // Dual Target
            // }
            turnSpeed -= vision.angleCorrect();
        }

        if (oi.getTurnCorrectRelease()) {
            if (vision.currentPipeline.get(0) != 2) {
                // NetworkTableHelper.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 2); // Switches to
                                                                                                        // default
                                                                                                        // driver
                                                                                                        // pipeline
            }
        }

        // if (oi.getAimbot() > 0) { 
        //     if(vision.currentPipeline.get(0) != 0) {
        //         SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 0);
        //     }
        //     turnSpeed += vision.angleCorrect();
        //     if (vision.instantaneousJerk > vision.JERK_COLLISION_THRESHOLD) {
        //         hatchIntake.pickupExtend();
        //     } else {
        //         hatchIntake.pickupRetract();
        //     }
        // }

        if (oi.getAimbotRelease()) {
            if (vision.currentPipeline.get(0) != 2) {
                // NetworkTableHelper.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 2); // Switches to
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
        // SmartDashboard.putNumber("Vision Jerk Value", vision.instantaneousJerk);
    }
}
