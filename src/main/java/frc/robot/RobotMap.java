package frc.robot;

public class RobotMap {
    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */

    // Controllers
    public static final int CONTROLLER_DRIVE_PORT = 0;
    public static final int CONTROLLER_OPERATOR_PORT = 1;
    public static final double CONTROLLER_DEADBAND = 0.08;

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
    public static final int HATCH_LIMIT_SWITCH_HATCH_ID = 1;

    // Hatch Intake: 0 is at top, positive means lower
    public static final double HATCH_PID_LOWERED = 10.0;            // Position of the hatch intake when lowered
    public static final double HATCH_PID_RAISED = 0.0;              // Position of the hatch intake when raised 
    public static double HATCH_MOTOR_MAX_SPEED = 1.0;

    public static double[] HATCH_PIDF = {1.0, 0.0, 0.0, 0.0};
    public static final double HATCH_PID_MAX_OUTPUT = 1.0;
    public static final double HATCH_PID_MIN_OUTPUT = -1.0;
    public static final double HATCH_PID_UPDATE_PERIOD = 0.020;     // How often the PID will be checked in sec
    public static final double HATCH_PID_TOLERANCE = 5;             // How close the PID can be 
    public static final double HATCH_PID_TIME = 0.01;               // How long the PID has to be within the tolerance in sec

    // Intake Arm
    public static final int INTAKE_ARM_MOTOR_ID = 6;
    public static final int INTAKE_ARM_LIMIT_SWITCH_ID = 0;

    // Intake Arm: 0 is at bottom, positive means higher
    public static final double INTAKE_ARM_LOWER_THRESHOLD = 0.0;         // Lower limit of the arm's motion
    public static final double INTAKE_ARM_UPPER_THRESHOLD = 10.0;        // Upper limit of the arm's motion
    public static final double INTAKE_ARM_PID_GROUND = 0.0;              // Target position for ground pickup
    public static final double INTAKE_ARM_PID_ROCKET = 5.0;              // Target position for rocket
    public static final double INTAKE_ARM_PID_CARGO = 10.0;              // Target position for cargo ship
    public static double INTAKE_ARM_MOTOR_MAX_SPEED = 1.0;

    public static final double[] INTAKE_ARM_PIDF = {1.0, 2.0, 3.0, 4.0}; 
    public static final double INTAKE_ARM_PID_UPDATE_PERIOD = 0.020;     // How often the PID will be checked in sec
    public static final double INTAKE_ARM_PID_TOLERANCE = 5;             // How close the PID can be
    public static final double INTAKE_ARM_PID_TIME = 0.01;               // How long the PID has to be on target in sec

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

    // Vision

    // turnCorrect() constants
    public static final double TURN_CORRECT_KP = -0.03;
    public static final double TURN_CORRECT_MIN_COMMAND = 0.05;
    public static final double TURN_CORRECT_MIN_ANGLE = 2.0; // Degrees

    // getInDistance() constants
    public static final int MEASUREMENT_AMOUNT = 60; // Inches
    public static final double[] DISTANCE_TO_PERCENT = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.6355735063552856, 1.4441826343536377, 2.230164051055908, 2.9016757011413574, 2.8803789615631104, 2.703350305557251, 2.6128392219543457, 2.441800117492676, 2.2082018852233887, 2.1562912464141846, 2.1216840744018555, 1.8987340927124023, 1.7483261823654175, 1.6731222867965698, 1.583942174911499, 1.3643200397491455, 1.386282205581665, 1.321726679801941, 1.2325466871261597, 1.0036070346832275, 1.162001371383667, 1.1460288763046265, 1.0495281219482422, 1.0688282251358032, 1.0328900814056396, 0.8012884259223938, 0.8711682558059692, 0.8092747330665588, 0.7314086556434631, 0.7646847367286682, 0.7367328405380249, 0.8412197232246399, 0.8372266888618469, 0.8312369585037231, 0.8119367957115173, 0.6455563306808472, 0.6395666003227234, 0.6528770327568054, 0.6408976316452026, 0.5443969964981079, 0.6415631771087646, 0.5810006856918335, 0.5889869332313538, 0.4818378984928131, 0.5730144381523132, 0.5497211813926697, 0.4938172996044159};
    public static final double DESIRED_TARGET_DISTANCE = 24;
    public static final double DISTANCE_CORRECT_KP = 4;
    public static final double DISTANCE_CORRECT_ERROR = 3.0;
}
