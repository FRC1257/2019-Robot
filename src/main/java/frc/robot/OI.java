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

    public boolean getDriveReverse() {
        return driveController.getBButtonPressed();
    }

    // Intake Arm
    public double getArmSpeed() {
        return squareInput(-operatorController.getY(Hand.kRight));
    }

    public boolean getArmRaise() {
        return operatorController.getBumperPressed(Hand.kLeft);
    }

    public boolean getArmLower() {
        return operatorController.getBumperPressed(Hand.kRight);
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
        return operatorController.getBackButtonPressed();
    }

    public boolean getClimbBackToggle() {
        return operatorController.getStartButtonPressed();
    }

    public boolean getClimbReset() {
        return operatorController.getDPadDownPressed();
    }

    public double getClimbDriveSpeed() {
        return squareInput(driveController.getForwardSpeed());
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
