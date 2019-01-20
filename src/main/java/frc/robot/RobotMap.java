package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */
    
     //Cargo Intake
     public static double CARGO_OUTTAKE_SPEED = 1.0;
     //Maximum possible speed for cargo ejection as a percentage of total motor capacity
     public static double CARGO_INTAKE_SPEED = -1.0;
     //Maximum possible speed for cargo intake as a percentage of total motor capacity
     public static final int CARGO_INTAKE_PORT = 1;
     //Port of Intake Motor
     public static final int OPERATOR_CONTROLLER = 1;
     //Xbox Controller Port


     //Infared Sensor
     //https://home.roboticlab.eu/en/examples/sensor/ir_distance
     public static final int CARGO_INFARED_PORT = 1;
     //Port of Infrared Sensor
     public static double CARGO_PONR = 30.0;
     //Point of no return at which the cargo needs to be pulled back; defines upper threshold for P-loop
     public static double CARGO_SENSOR_LOWER_THRESHOLD = 10.0;
     //Furtherst distance at which cargo is too close to be accurately measured; defines lower threshold for P-loop
     public static double kP = CARGO_INTAKE_SPEED/CARGO_PONR;
}
