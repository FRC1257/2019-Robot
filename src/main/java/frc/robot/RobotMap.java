package frc.robot;

public class RobotMap {

    // PID for motors
    public static final double DRIVE_PID_LEFT[] = {0, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final double DRIVE_PID_RIGHT[] = {0, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final int DRIVE_NEO_CONSTS[] = {60, 1, 5500, 10}; // for NEO: stall current (Amps), running current (Amps), RPM limit, limitRPM
    public static final int DRIVE_NEO_RPMLIMIT = 5500; // RPM
    public static final double DRIVE_P_WASHOUT = 1.0; // Placeholder: P control for washout
    public static final double DRIVE_DIAMETER = 6.0;
    public static final double PI = 3.1415926535897932;
    // Channel IDs
    public static final int DRIVE_MOTORS[] = {0, 1, 2, 3}; // In this order: FL, FR, BL, BR
    public static final int DRIVE_CONTROLLER_PORT = 0;
}
