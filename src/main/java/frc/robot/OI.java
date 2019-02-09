package frc.robot;

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

    // Drivetrain
    public double getDriveForwardSpeed() {
        return applyDeadband(driveController.getForwardSpeed());
    }

    public double getDriveTurnSpeed() {
        return applyDeadband(driveController.getTurnSpeed());
    }

    public boolean getDriveToggleReverse() {
        return driveController.getBButtonPressed();
    }

    public boolean getDriveTestFL() {
        return driveController.getXButton();
    }

    public boolean getDriveTestFR() {
        return driveController.getYButton();
    }

    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public double applyDeadband(double in) {
        if(Math.abs(in) < RobotMap.NEO_DEADBAND) {
            return 0;
        }
        return in;
    }
}