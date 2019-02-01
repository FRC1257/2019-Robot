package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */

    public static final int CONTROLLER_DRIVE_PORT = 0;
    public static final int CONTROLLER_OPERATOR_PORT = 0;

    // Climb
    public static final int CLIMB_FRONT_MOTOR = 0;
    public static final int CLIMB_BACK_MOTOR = 1;

    public static final int CLIMB_FRONT_SOLENOID_FORWARD = 0;
    public static final int CLIMB_FRONT_SOLENOID_REVERSE = 1;
    public static final int CLIMB_BACK_SOLENOID_FORWARD = 2;
    public static final int CLIMB_BACK_SOLENOID_REVERSE = 3;
    
    // Climb Smart Dashboard
    public static final String CLIMB_SD_FRONT_RAISED = "Climb Front Raised";
    public static final String CLIMB_SD_BACK_RAISED = "Climb Back Raised";
    public static final String CLIMB_SD_DRIVE_SPEED_FRONT = "Climb Front Drive Speed";
    public static final String CLIMB_SD_DRIVE_SPEED_BACK = "Climb Back Drive Speed";
}
