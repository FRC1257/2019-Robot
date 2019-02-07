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
        return driveController.getForwardSpeed();
    }
    public double getDriveTurnSpeed() {
        return driveController.getTurnSpeed();
    }

    // Intake Arm
    public double getArmSpeed() {
        return operatorController.getY(Hand.kRight);
    }
    public boolean getArmRaise() {
        return operatorController.getAButtonPressed();
    }
    public boolean getArmLower() {
        return operatorController.getBButtonPressed();
    }
    

    // Cargo Intake
    public boolean getCargoShootButton() {
        return operatorController.getAButton();
    }

    
    // Hatch Intake
    public boolean getHatchPivotToggle() {
        return operatorController.getXButtonPressed();
    }
    public boolean getHatchPickup() {
        return operatorController.getAButton();
    }
    public boolean getHatchEject() {
        return operatorController.getBButton();
    }
    public double getHatchPivot() {
        return operatorController.getY(Hand.kLeft);
    }

    
    // Climb
    public boolean getClimbAdvance() {
        return operatorController.getAButtonPressed();
    }
    public boolean getClimbFrontToggle() {
        return operatorController.getXButtonPressed();
    }
    public boolean getClimbBackToggle() {
        return operatorController.getYButtonPressed();
    }
    public boolean getClimbReset() {
        return operatorController.getBButtonPressed();
    }
    public double getClimbDriveSpeed() {
        return driveController.getY(Hand.kLeft);
    }
    
    
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }
}
