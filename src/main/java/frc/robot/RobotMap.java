package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */
    public static final int CONTROLLER_DRIVE_PORT = 0;
    public static final int CONTROLLER_OPERATOR_PORT = 0;
    // climb
    //SmartDashboard
    public static final String  FRONT_CLIMB_ON = "State of Front Solenoid";
    public static final String  BACK_CLIMB_ON = "State of Back Solenoid";
    public static final String  CLIMB_MOTOR_VELOCITY = "Speed of Climb Motors";
    // channels
    public static final int CLIMB_MOTOR_F = 0;
    public static final int CLIMB_MOTOR_B = 1; // motors

    public static final int CLIMB_SOLENOID_F_PORT_1 = 0;
    public static final int CLIMB_SOLENOID_F_PORT_2 = 1; // 0,1 for front solenoid
    public static final int CLIMB_SOLENOID_B_PORT_1 = 2;
    public static final int CLIMB_SOLENOID_B_PORT_2 = 3; // 2,3 for back solenoid
    //
    
}
