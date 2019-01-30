package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.util.SnailController;

public class OI {

    /**
     * Contains all of the mappings for controls for our robot
     */
    
    private static OI instance = null;

    public SnailController driveController;
    public SnailController operatorController;

    private OI() {
        driveController = new SnailController(RobotMap.DRIVE_CONTROLLER_PORT);
        operatorController = new SnailController(RobotMap.OPERATOR_CONTROLLER_PORT);
    }

    // Hatch Intake
    public boolean getTogglePivot() {
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

    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }
}