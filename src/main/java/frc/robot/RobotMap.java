package frc.robot;

public class RobotMap {

    public static final double PID_LEFT[] = {0.1, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final double PID_RIGHT[] = {0.1, 1.0, 0, 0}; // Placeholder: F, P, I, D
    public static final int NEO_CONSTS[] = {105, 1, 5500, 10}; // for NEO: stall current, running current, RPM limit, limitRPM
    public static final int NEO_RPMLIMIT = 5500;

    public static final int MOTORS[] = {0, 1, 2, 3}; // In this order: FL, FR, BL, BR
}