package frc.robot;

public class RobotMap {

    // PID for motors
    public static final double PID_LEFT[] = {0, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final double PID_RIGHT[] = {0, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final int TALON_RPMLIMIT = 5500; // RPM
    public static final double P_WASHOUT = 1.0; // Placeholder: P control for washout

    // Channel IDs
    public static final int MOTORS[] = {3, 2, 6, 1}; // In this order: FL, FR, BL, BR
    public static final int CONTROLLER = 0; // Controller ID
}