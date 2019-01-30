package frc.robot;

public class RobotMap {

    // PID for motors, both of them.
    public static final double DRIVE_PID_LEFT_P = 1.0;
    public static final double DRIVE_PID_LEFT_I = 0.0;
    public static final double DRIVE_PID_LEFT_D = 0.0;
    public static final double DRIVE_PID_LEFT_F = 0.0;
    public static final double DRIVE_PID_RIGHT_P = 1.0;
    public static final double DRIVE_PID_RIGHT_I = 0.0;
    public static final double DRIVE_PID_RIGHT_D = 0.0;
    public static final double DRIVE_PID_RIGHT_F = 0.0;
    public static final double DRIVE_P_WASHOUT = 1.0; 

    // Information about NEO motors.
    public static final int NEO_STALL_LIMIT = 60;
    public static final int NEO_RUN_CURRENT = 1;
    public static final int NEO_MIN_RPM = 10;
    public static final int NEO_MAX_RPM = 5500;

    // Drivetrain wheel information.
    public static final double DRIVE_DIAMETER = 6.0;
    public static final double PI = 3.1415926535897932;

    // Channel IDs
    public static final int DRIVE_FL = 0;
    public static final int DRIVE_FR = 1;
    public static final int DRIVE_BL = 2;
    public static final int DRIVE_BR = 3;
    public static final int DRIVE_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;

    // Keys for SmartDashboard.
    public static final String RESET_STATUS = "EncoderReset: ";
    public static final String CURRENT_DISTANCE = "RPM traveled: ";
    public static final String CURRENT_DISTANCE_INCHES = "Inches traveled: ";
    public static final String CURRENT_DISTANCE_FEET = "Feet traveled: ";
    public static final String CURRENT_SPEED = "Current speed (RPM): ";
    public static final String CURRENT_SPEED_INCHES = "Current speed (inches per minutes): ";
    public static final String CURRENT_SPEED_FEET = "Current speed (feet per minute): ";
}
