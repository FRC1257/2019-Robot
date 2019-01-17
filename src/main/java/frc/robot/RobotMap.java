package frc.robot;

public class RobotMap {

    // PID for motors
    public static final double PID_LEFT[] = {0, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final double PID_RIGHT[] = {0, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final int NEO_CONSTS[] = {105, 1, 5500, 10}; // for NEO: stall current (Amps), running current (Amps), RPM limit, limitRPM
    public static final int NEO_RPMLIMIT = 5500; // RPM
    public static final double P_WASHOUT = 1.0; // Placeholder: P control for washout
    public static final double diameter = 6.0;
    public static final double pi = 3.14159265359;
    // Channel IDs
    public static final int MOTORS[] = {0, 1, 2, 3}; // In this order: FL, FR, BL, BR
    public static final int controllerPort = 0;
}