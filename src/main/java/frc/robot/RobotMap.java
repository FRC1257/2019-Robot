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

    // Hatch Intake: 0 is at top, positive means lower
    public static final double HATCH_PID_LOWERED = 10.0;            // Position of the hatch intake when lowered
    public static final double HATCH_PID_RAISED = 0.0;              // Position of the hatch intake when raised 

    public static final double[] HATCH_PIDF = {1.0, 0.0, 0.0, 0.0};

    public static final double HATCH_PID_UPDATE_PERIOD = 0.020;     // How often the PID will be checked in sec
    public static final double HATCH_PID_TOLERANCE = 5;             // How close the PID can be 
    public static final double HATCH_PID_TIME = 0.01;               // How long the PID has to be within the tolerance in sec
    
}
