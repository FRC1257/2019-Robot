package frc.robot;

public class RobotMap {
    /**
     * Contains constants for use without the entire robot All constants should be
     * ordered by subsystem/use The units of each measurement should be specified in
     * a comment
     */

    // Controllers
    public static final int CONTROLLER_DRIVE_PORT = 0;
    public static final int CONTROLLER_OPERATOR_PORT = 1;
    public static final double CONTROLLER_DEADBAND = 0.08;
    public static final int CONTROLLER_POV = 0;

    // Drive
    public static final int DRIVE_FRONT_LEFT = 4;
    public static final int DRIVE_FRONT_RIGHT = 3;
    public static final int DRIVE_BACK_LEFT = 2;
    public static final int DRIVE_BACK_RIGHT = 1;

    // Hatch Intake
    public static final int HATCH_PIVOT_MOTOR_ID = 5;
    public static final int HATCH_PICKUP_SOLENOID_ID = 4;
    public static final int HATCH_EJECT_SOLENOID_ID = 5;
    public static final int HATCH_LIMIT_SWITCH_PIVOT_ID = 2;
    public static final int HATCH_LIMIT_SWITCH_HATCH_ID = 0;

    // Hatch Intake: 0 is at top, positive means lower
    public static final double HATCH_PID_LOWERED = 10.0; // Position of the hatch intake when lowered
    public static final double HATCH_PID_RAISED = 0.0; // Position of the hatch intake when raised
    public static double HATCH_MOTOR_MAX_SPEED = 1.0;

    public static double[] HATCH_PIDF = { 1.0, 0.0, 0.0, 0.0 };
    public static final double HATCH_PID_MAX_OUTPUT = 1.0;
    public static final double HATCH_PID_MIN_OUTPUT = -1.0;
    public static final double HATCH_PID_UPDATE_PERIOD = 0.020; // How often the PID will be checked in sec
    public static final double HATCH_PID_TOLERANCE = 5; // How close the PID can be
    public static final double HATCH_PID_TIME = 0.01; // How long the PID has to be within the tolerance in sec

    // Intake Arm
    public static final int INTAKE_ARM_MOTOR_ID = 6;
    public static final int INTAKE_ARM_LIMIT_SWITCH_ID = 1;

    // Intake Arm: 0 is at bottom, positive means higher
    public static final double INTAKE_ARM_LOWER_THRESHOLD = 0.0; // Lower limit of the arm's motion
    public static final double INTAKE_ARM_UPPER_THRESHOLD = 10.0; // Upper limit of the arm's motion
    public static final double INTAKE_ARM_PID_GROUND = 0.0; // Target position for ground pickup
    public static final double INTAKE_ARM_PID_ROCKET = 10.0; // Target position for rocket
    public static final double INTAKE_ARM_PID_CARGO = 20.0; // Target position for cargo ship
    public static final double INTAKE_ARM_PID_RAISED = 25.0;
    public static double INTAKE_ARM_MOTOR_MAX_SPEED = 1.0;

    public static final double[] INTAKE_ARM_PIDF = { 0.1, 0.0, 0.0, 0.0 };
    public static final double INTAKE_ARM_PID_MAX_OUTPUT = 1.0;
    public static final double INTAKE_ARM_PID_MIN_OUTPUT = -1.0;
    public static final double INTAKE_ARM_PID_UPDATE_PERIOD = 0.020; // How often the PID will be checked in sec
    public static final double INTAKE_ARM_PID_TOLERANCE = 0.5; // How close the PID can be
    public static final double INTAKE_ARM_PID_TIME = 0.01; // How long the PID has to be on target in sec

    // Cargo Intake
    public static final int CARGO_INTAKE_MOTOR_ID = 7;
    public static double CARGO_OUTTAKE_SPEED = -1.0; // percentage
    public static double CARGO_INTAKE_SPEED = 0.5; // percentage

    // Climb
    public static final int CLIMB_FRONT_MOTOR = 9;
    public static final int CLIMB_BACK_MOTOR = 8;

    public static final int CLIMB_FRONT_SOLENOID_FORWARD = 0;
    public static final int CLIMB_FRONT_SOLENOID_REVERSE = 1;
    public static final int CLIMB_BACK_SOLENOID_FORWARD = 2;
    public static final int CLIMB_BACK_SOLENOID_REVERSE = 3;

    public static double CLIMB_MOTOR_MAX_SPEED = 1.0;
    public static final double CLIMB_CRITICAL_ANGLE = 20.0;
    public static final double CLIMB_UPDATE_PERIOD = 0.020;
}
