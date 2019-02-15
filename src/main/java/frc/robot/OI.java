package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.util.SnailController;
import static frc.util.SnailController.*;

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
        return squareInput(operatorController.getY(Hand.kRight));
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
    public boolean getCargoIntakeButton() {
        return operatorController.getBButton();
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
        return squareInput(operatorController.getY(Hand.kLeft));
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
        return squareInput(driveController.getY(Hand.kLeft));
    }
    
    
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }
}
