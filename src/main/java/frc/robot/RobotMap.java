package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */

    // Vision
    public static final int MEASRUEMENT_AMOUNT = 120; // A total of 120 measurements in inches
    public static final double[] DISTANCE_TO_PERCENT = {}; // Each index of the array represents distance in inches. Each value represents the total % of the screen taken up by the target at that distance

    // turnCorrect() constants
    public static final double TURN_CORRECT_KP = -0.03;
    public static final double TURN_CORRECT_MIN_COMMAND = 0.05;
    public static final double TURN_CORRECT_MIN_ANGLE = 2.0;
}
