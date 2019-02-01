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