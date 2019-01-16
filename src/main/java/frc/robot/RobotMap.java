package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */
    
     // Cargo Intake
     public static final int CARGO_P = 4;
     //Proportional Value for Cargo Intake
     public static final double OUTTAKE_SPEED = 1.0;
     //Maximum possible speed for cargo ejection as a percentage of total motor capacity
     public static final double INTAKE_SPEED = -1.0;
     //Maximum possible speed for cargo intake as a percentage of total motor capacity
     public static final int CARGO_INTAKE_PORT = 1;
     //Port of Intake Motor
     public static final int OPERATOR_CONTROLLER = 1;
     //Xbox Controller Port

     // Infared Sensor
     public static final int CARGO_INFARED_PORT = 1;
     public static final int CARGO_PONR = 20;
     // Point of no return for when cargo starts to fall out (cm) - Random value, must input real value
}