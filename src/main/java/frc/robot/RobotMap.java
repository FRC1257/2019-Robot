package frc.robot;

public class RobotMap {

    /**
     * Contains constants for use without the entire robot
     * All constants should be ordered by subsystem/use
     * The units of each measurement should be specified in a comment
     */
    
     // CARGO INTAKE MOTOR
     
     public static final int CARGO_INTAKE_PORT = 1;
     public static double CARGO_MAX_OUTTAKE_SPEED = 1.0; // percentage
     public static double CARGO_MAX_INTAKE_SPEED = -1.0;
        // ideal speed twice that of robot drive speed (12ft/s * 2 = 24 ft/s)
    
     // CARGO INFRARED SENSOR
     // https://home.roboticlab.eu/en/examples/sensor/ir_distance
     
     // Port of Infrared Sensor
     public static final int CARGO_INFARED_PORT = 1;
     // Factor for converting analogue voltage to centimeter value
     public static final double CARGO_INFRARED_CONVERSION_FACTOR = (2.6/-70); // incorrect conversion factor
     // Furthest distance at which cargo is too close to be accurately measured; defines lower threshold for P-loop
     public static double CARGO_SENSOR_LOWER_THRESHOLD = 10.0;

      public static final double[] CARGO_KNOWN_VOLTAGE_PTS = {
         0, 1, 2, 3, 4, 5, 6, 7, 8, 9
      };
      public static final double[] CARGO_KNOWN_DISTANCE_PTS = {
         0, 1, 2, 3, 4, 5, 6, 7, 8, 9
      }; // sample values - not real data points

     
    
    
     // MISCELLANEOUS
    
     // Xbox Controller Port
     public static final int OPERATOR_CONTROLLER = 1;
}
