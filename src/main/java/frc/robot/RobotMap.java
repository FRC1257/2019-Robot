package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */

    // Controllers
    public static final int CONTROLLER_DRIVE_PORT = 1;
    public static final int CONTROLLER_OPERATOR_PORT = 2;

    // Intake Arm
    public static final int INTAKE_ARM_MOTOR_PORT = 0;
    public static final double TOP_TARGET_POSITION = 0;
    public static final double BOTTOM_TARGET_POSITION = 10;
    public static final double LIMIT_SWITCH_ID = 0;

    public static final double[] INTAKE_ARM_PID_CONSTANTS = {1.0, 2.0, 3.0, 4.0}; // test values

    public static final double HATCH_PID_TOLERANCE = 5;         // How close the PID can be
    public static final double HATCH_PID_TIME = 0.01;           // How long the PID has to be within the tolerance in seconds
}
