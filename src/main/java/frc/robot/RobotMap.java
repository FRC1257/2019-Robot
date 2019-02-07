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

    // Intake Arm
    public static final int INTAKE_ARM_MOTOR_ID = 0;
    public static final int INTAKE_ARM_LIMIT_SWITCH_ID = 0;

    // Intake Arm: 0 is at bottom, positive means higher
    public static final double INTAKE_ARM_LOWER_THRESHOLD = 0.0;         // Lower limit of the arm's motion
    public static final double INTAKE_ARM_UPPER_THRESHOLD = 10.0;        // Upper limit of the arm's motion
    public static final double INTAKE_ARM_PID_GROUND = 0.0;              // Target position for ground pickup
    public static final double INTAKE_ARM_PID_ROCKET = 5.0;              // Target position for rocket
    public static final double INTAKE_ARM_PID_CARGO = 10.0;               // Target position for cargo ship

    public static final double[] INTAKE_ARM_PIDF = {1.0, 2.0, 3.0, 4.0}; 
    public static final double INTAKE_ARM_PID_UPDATE_PERIOD = 0.020;     // How often the PID will be checked in sec
    public static final double INTAKE_ARM_PID_TOLERANCE = 5;             // How close the PID can be
    public static final double INTAKE_ARM_PID_TIME = 0.01;               // How long the PID has to be on target in sec

	// Cargo Intake
	
	public static final int CARGO_INTAKE_MOTOR_ID = 1;
	public static double CARGO_OUTTAKE_SPEED = -1.0; // percentage
	public static double CARGO_INTAKE_SPEED = 1.0; // percentage
}
