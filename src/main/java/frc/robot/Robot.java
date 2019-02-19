package frc.robot;


import frc.subsystems.*;
import frc.util.*;
import frc.util.snail_vision.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;

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

        // Vision recording measurements for area to distance
        if(oi.getRecordArea()){
            vision.recordTargetArea();
        }
        if(oi.getSaveArea()){
            vision.clearTargetArea();
        }
        if(oi.getPrintArea()){
            vision.printTargetArea();
        }
        if(oi.getResetArea()){
            vision.resetTargetArea();
        }
    }

    public void teleopFunctionality() {
        driveSpeed = 0;
        turnSpeed = 0;

        // Vision
        vision.networkTableFunctionality(NetworkTableInstance.getDefault().getTable("limelight"));
        visionFunctionality();

        
        // Drive
        driveSpeed += oi.getDriveForwardSpeed();
        turnSpeed += oi.getDriveTurnSpeed();
        drive.drive(driveSpeed, turnSpeed);

        
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
    }

    public void visionFunctionality(){
        if(oi.getTurnCorrect() > 0 && oi.getTurnCorrect() < 0.9){
            if(vision.currentPipeline.get(0) != 1){
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 1); // Changes to 1 target to expand vision
                turnSpeed += vision.angleCorrect();
            }
        }
        else if(oi.getTurnCorrect() > 0.9){
            if(vision.currentPipeline.get(0) != 0){
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 0); // Dual Target
                turnSpeed += vision.angleCorrect();
            }
        }
        
        if(oi.getTurnCorrectRelease()){
            if(vision.currentPipeline.get(0) != 2){
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 2); // Switches to default driver pipeline
            }
        }

        if(oi.getAimbot() > 0){
            if(vision.currentPipeline.get(0) != 1){
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 1); // Changes to 1 target to expand vision
                driveSpeed += vision.getInDistance(vision.TARGETS.get(0)); // Needs to be updated, but currently only 1 target
                turnSpeed += vision.angleCorrect();
            }
        }
        
        if(oi.getAimbotRelease()){
            if(vision.currentPipeline.get(0) != 2){
                SnailVision.changePipeline(NetworkTableInstance.getDefault().getTable("limelight"), 2); // Switches to default driver pipeline
            }
        }
    }
}
