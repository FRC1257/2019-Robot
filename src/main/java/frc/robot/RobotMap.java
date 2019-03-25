package frc.robot;

import frc.util.snail_vision.*;

public class RobotMap {
    /**
     * Contains constants for use without the entire robot. All constants should be
     * ordered by subsystem/use. The units of each measurement should be specified in
     * a comment
     */

    // General
    public static final int NEO_CURRENT_LIMIT = 50; // amps
    public static final int PCM_SECONDARY_ID = 10;

    // Controllers
    public static final int CONTROLLER_DRIVE_PORT = 0;
    public static final int CONTROLLER_OPERATOR_PORT = 1; 
    public static final double CONTROLLER_DEADBAND = 0.08; // Deadband for the joysticks of the controllers
    public static final int CONTROLLER_POV = 0; // Id for the DPad of the controllers

    // Drive
    public static final int DRIVE_FRONT_LEFT = 4;
    public static final int DRIVE_FRONT_RIGHT = 3;
    public static final int DRIVE_BACK_LEFT = 2;
    public static final int DRIVE_BACK_RIGHT = 1;

    public static double[] DRIVE_TURN_PIDF = { 0.01, 0.0, 0.0, 0.0 };
    public static double MAX_TURN_SPEED = 0.8;

    // Hatch Intake
    public static final int HATCH_INTAKE_MOTOR_ID = 11;
    public static double HATCH_INTAKE_SPEED = 1.0;
    public static double HATCH_OUTTAKE_SPEED = -1.0;

    // Hatch Intake: 0 is at top, positive means lower
    public static final double HATCH_PID_LOWERED = 11.0; // Position of the hatch intake when lowered
    public static final double HATCH_PID_RAISED = -0.7; // Position of the hatch intake when raised
    public static double HATCH_MOTOR_MAX_SPEED = 1.0;

    public static double[] HATCH_PIDF = { 0.045, 0.0, 0.0, 0.0 };
    public static final double HATCH_PID_MAX_OUTPUT = 0.8;
    public static final double HATCH_PID_MIN_OUTPUT = -0.8;

    // Intake Arm
    public static final int INTAKE_ARM_MOTOR_ID = 6;
    public static final int INTAKE_ARM_LIMIT_SWITCH_ID = 1;

    // Intake Arm: 0 is at bottom, positive means higher
    public static final double INTAKE_ARM_LOWER_THRESHOLD = 0.0; // Lower limit of the arm's motion
    public static final double INTAKE_ARM_UPPER_THRESHOLD = 10.0; // Upper limit of the arm's motion
    public static final double INTAKE_ARM_PID_GROUND = 0.0; // Target position for ground pickup
    public static final double INTAKE_ARM_PID_ROCKET = 11.0; // Target position for rocket
    public static final double INTAKE_ARM_PID_CARGO = 18.5; // Target position for cargo ship
    public static final double INTAKE_ARM_PID_RAISED = 29.5; // Target position for straight up
    public static double INTAKE_ARM_MOTOR_MAX_SPEED = 1.0;

    public static final double[] INTAKE_ARM_PIDF = { 0.1, 0.0, 0.0, 0.0 };
    public static final double INTAKE_ARM_PID_MAX_OUTPUT = 1.0;
    public static final double INTAKE_ARM_PID_MIN_OUTPUT = -1.0;

    // Cargo Intake
    public static final int CARGO_INTAKE_MOTOR_ID = 7;
    public static double CARGO_OUTTAKE_SPEED = -0.8; // percentage
    public static double CARGO_INTAKE_SPEED = 1.0; // percentage
    public static double CARGO_CONSTANT_INTAKE_SPEED = 0.2; // percentage

    // Climb
    public static final int CLIMB_FRONT_MOTOR = 9;
    public static final int CLIMB_BACK_MOTOR = 8;

    public static final int CLIMB_FRONT_SOLENOID_FORWARD = 0;
    public static final int CLIMB_FRONT_SOLENOID_REVERSE = 1;
    public static final int CLIMB_BACK_SOLENOID_FORWARD = 2;
    public static final int CLIMB_BACK_SOLENOID_REVERSE = 3;

    public static double CLIMB_MOTOR_MAX_SPEED = 1.0;
    public static final double CLIMB_CRITICAL_ANGLE = 5.0; // Critical angle before the climb stabilizer kicks in
    public static final double CLIMB_UPDATE_PERIOD = 0.020; // How often the climb stabilizer will be run in seconds
    

    // Vision
    public static final double[] AREA_TO_DISTANCE_ROCKET = {1};
    public static final double[] AREA_TO_DISTANCE_SHIP = {1};

    public static void initializeVision(SnailVision vision) {
        vision.ANGLE_CORRECT_P = -0.02;
        vision.ANGLE_CORRECT_F = 0.2;
        vision.ANGLE_CORRECT_MIN_ANGLE = 2.0; // degrees
        
        vision.GET_IN_DISTANCE_P = 4.0;
        vision.GET_IN_DISTANCE_ERROR = 3.0; // inches
        vision.DISTANCE_ESTIMATION_METHOD = "area";

        vision.JERK_COLLISION_THRESHOLD = 1;

        vision.TARGETS.add(new Target(0, 12, 60, AREA_TO_DISTANCE_ROCKET)); // Rocket
        vision.TARGETS.add(new Target(0, 12, 60, AREA_TO_DISTANCE_SHIP)); // Cargo Ship
    }
}
