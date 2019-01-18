package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */
    public static final int HATCH_PIVOT_MOTOR_ID = 1;
    public static final int HATCH_PICKUP_ID = 1;
    public static final int HATCH_EJECT_ID = 2;
    public static final int HATCH_LIMIT_ID = 1;

    public static final double HATCH_PID_P_VALUE = 1;
    public static final double HATCH_PID_I_VALUE = 1;
    public static final double HATCH_PID_D_VALUE = 1;
    public static final double HATCH_PID_FF_VALUE = 1;
    public static final double HATCH_PID_UPDATE_PERIOD = 0.020; // How often the PID will be checked in seconds
    public static final double HATCH_PID_TOLERANCE = 5;  // How close the PID can be 
    public static final double HATCH_PID_COUNT = 5;      // Amount of times the PID has to be within the tolerance
}
