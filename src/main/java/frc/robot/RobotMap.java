package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */

    // Vision

    // turnCorrect() constants
    public static final double TURN_CORRECT_KP = -0.03;
    public static final double TURN_CORRECT_MIN_COMMAND = 0.05;
    public static final double TURN_CORRECT_MIN_ANGLE = 2.0; // Degrees

    // getInDistance() constants
    public static final int MEASRUEMENT_AMOUNT = 60; // Inches
    public static final double[] DISTANCE_TO_PERCENT = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.6355735063552856, 1.4441826343536377, 2.230164051055908, 2.9016757011413574, 2.8803789615631104, 2.703350305557251, 2.6128392219543457, 2.441800117492676, 2.2082018852233887, 2.1562912464141846, 2.1216840744018555, 1.8987340927124023, 1.7483261823654175, 1.6731222867965698, 1.583942174911499, 1.3643200397491455, 1.386282205581665, 1.321726679801941, 1.2325466871261597, 1.0036070346832275, 1.162001371383667, 1.1460288763046265, 1.0495281219482422, 1.0688282251358032, 1.0328900814056396, 0.8012884259223938, 0.8711682558059692, 0.8092747330665588, 0.7314086556434631, 0.7646847367286682, 0.7367328405380249, 0.8412197232246399, 0.8372266888618469, 0.8312369585037231, 0.8119367957115173, 0.6455563306808472, 0.6395666003227234, 0.6528770327568054, 0.6408976316452026, 0.5443969964981079, 0.6415631771087646, 0.5810006856918335, 0.5889869332313538, 0.4818378984928131, 0.5730144381523132, 0.5497211813926697, 0.4938172996044159};
}
