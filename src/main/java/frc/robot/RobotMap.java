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

    // Hatch Intake
    public static final int HATCH_PIVOT_MOTOR_ID = 1;
    public static final int HATCH_PICKUP_SOLENOID_ID = 1;
    public static final int HATCH_EJECT_SOLENOID_ID = 2;
    public static final int HATCH_LIMIT_SWITCH_PIVOT_ID = 1;
    public static final int HATCH_LIMIT_SWITCH_HATCH_ID = 2;

    public static final double HATCH_PID_LOWERED = 0;           // Position of the hatch intake when lowered
    public static final double HATCH_PID_RAISED = 10;           // Position of the hatch intake when raised 

    public static final double HATCH_PID_P = 1;
    public static final double HATCH_PID_I = 1;
    public static final double HATCH_PID_D = 1;
    public static final double HATCH_PID_F = 1;
    public static final double HATCH_PID_UPDATE_PERIOD = 0.020; // How often the PID will be checked in seconds
    public static final double HATCH_PID_TOLERANCE = 5;         // How close the PID can be 
    public static final double HATCH_PID_TIME = 0.01;           // How long the PID has to be within the tolerance in seconds
}
