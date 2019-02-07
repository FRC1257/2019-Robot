package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.util.SnailController;

public class OI {

    /*
     * Contains all of the mappings for controls for our robot
     */
    
    private static OI instance = null;

    public SnailController driveController;
    public SnailController operatorController;

    private OI() {
        driveController = new SnailController(RobotMap.CONTROLLER_DRIVE_PORT);
        operatorController = new SnailController(RobotMap.CONTROLLER_OPERATOR_PORT);
    }


    // Drive
    public double getDriveForwardSpeed() {
        return Math.abs(driveController.getForwardSpeed()) < 0.08 ? 0 : driveController.getForwardSpeed();
    }
    public double getDriveTurnSpeed() {
        return Math.abs(driveController.getTurnSpeed()) < 0.08 ? 0 : driveController.getTurnSpeed();
    }

    // Intake Arm
    public double getArmSpeed() {
        return Math.abs(operatorController.getY(Hand.kRight)) < 0.08 ? 0 : operatorController.getY(Hand.kRight);
    }
    public boolean getArmRaise() {
        return false;
        // return operatorController.getAButtonPressed();
    }
    public boolean getArmLower() {
        return false;
        // return operatorController.getBButtonPressed();
    }
    

    // Cargo Intake
    public boolean getCargoShootButton() {
        return operatorController.getAButton();
    }

    
    // Hatch Intake
    public boolean getHatchPivotToggle() {
        return false;
        // return operatorController.getXButtonPressed();
    }
    public boolean getHatchPickup() {
        return operatorController.getXButton();
    }
    public boolean getHatchEject() {
        return operatorController.getYButton();
    }
    public double getHatchPivot() {
        return Math.abs(operatorController.getY(Hand.kLeft)) < 0.08 ? 0 : operatorController.getY(Hand.kLeft);
    }

    
    // Climb
    public boolean getClimbAdvance() {
        return false;
        // return operatorController.getAButtonPressed();
    }
    public boolean getClimbFrontToggle() {
        return operatorController.getBumperPressed(Hand.kRight);
    }
    public boolean getClimbBackToggle() {
        return operatorController.getBumperPressed(Hand.kLeft);
    }
    public boolean getClimbReset() {
        return false;
        // return operatorController.getBButtonPressed();
    }
    public double getClimbDriveSpeed() {
        return Math.abs(driveController.getY(Hand.kLeft)) < 0.08 ? 0 : driveController.getY(Hand.kLeft);
    }
    
    
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }
}
