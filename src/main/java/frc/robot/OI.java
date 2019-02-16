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
        return squareInput(-operatorController.getY(Hand.kRight));
    }
    public boolean getArmRaise() {
        // return false;
        return operatorController.getBumperPressed(Hand.kLeft);
    }
    public boolean getArmLower() {
        // return false;
        return operatorController.getBumperPressed(Hand.kRight);
    }
    public boolean getArmBreak() {
        return operatorController.getStickButton(Hand.kRight);
    }
    

    // Cargo Intake
    public boolean getCargoShootButton() {
        return operatorController.getAButton();
    }
    public boolean getCargoIntakeButton() {
        return operatorController.getBButton();
    }

    
    // Hatch Intake
    public boolean getHatchLower() {
        return operatorController.getTriggerAxis(Hand.kLeft) > 0.5;
    }
    public boolean getHatchRaise() {
        return operatorController.getTriggerAxis(Hand.kRight) > 0.5;
    }
    public boolean getHatchPickup() {
        return operatorController.getYButton();
    }
    public boolean getHatchEject() {
        return operatorController.getXButton();
    }
    public double getHatchPivot() {
        return squareInput(operatorController.getY(Hand.kLeft));
    }

    
    // Climb
    public boolean getClimbAdvance() {
        return operatorController.getDPadUpPressed();
    }
    public boolean getClimbFrontToggle() {
        return operatorController.getDPadLeftPressed();
        // return operatorController.getBumperPressed(Hand.kLeft);
    }
    public boolean getClimbBackToggle() {
        return operatorController.getDPadRightPressed();
        // return operatorController.getBumperPressed(Hand.kRight);
    }
    public boolean getClimbReset() {
        return operatorController.getDPadDownPressed();
    }
    public double getClimbDriveSpeed() {
        return squareInput(driveController.getY(Hand.kLeft));
    }
    
    public void updateControllers() {
        driveController.updatePrevDPad();
        operatorController.updatePrevDPad();

        driveController.outputPrev("Drive");
        operatorController.outputPrev("Operator");
    }
    
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }
}
