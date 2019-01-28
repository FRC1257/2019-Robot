package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CargoIntake {
    
    private static CargoIntake instance = null;
    
    private AnalogInput cargoInfrared;
    private VictorSPX intakeMotor;

    private CargoIntake() {
        intakeMotor = new VictorSPX(RobotMap.CARGO_INTAKE_PORT);
        cargoInfrared = new AnalogInput(RobotMap.CARGO_INFARED_PORT);
    }
    
    // Infrared analog voltage >> Distance in centimeters
    public double getDistanceToCargo() {
        double volt = cargoInfrared.getAverageVoltage();
        return interpLinear(RobotMap.CARGO_KNOWN_VOLTAGE_PTS, RobotMap.CARGO_KNOWN_DISTANCE_PTS, volt);
        //return volt*RobotMap.CARGO_INFRARED_CONVERSION_FACTOR;
    }
    
    
    // Smart Dashboard
    
    // Diagnostic Data to  continually pushed to Shuffle Board during teleopPeriodic
    public void telemetry() {
        SmartDashboard.putNumber("Distance to Cargo", getDistanceToCargo());
        SmartDashboard.putNumber("Voltage Recieved from Analog Sensor", cargoInfrared.getAverageVoltage());
       
    }
    
    // Constants initialized in Shuffle Board during robotInit 
    public void setConstantTuning() {
        SmartDashboard.putNumber("Intake Speed", RobotMap.CARGO_MAX_INTAKE_SPEED);
	    SmartDashboard.putNumber("Outake Speed", RobotMap.CARGO_MAX_OUTTAKE_SPEED);
        SmartDashboard.putNumber("Point Of No Return", RobotMap.CARGO_SENSOR_UPPER_THRESHOLD);
    }
    
    // Updated Constants to be continually read from Shuffle Board during teleopPeriodic 
    public void getConstantTuning() {
        RobotMap.CARGO_MAX_INTAKE_SPEED = SmartDashboard.getNumber("Intake Speed", RobotMap.CARGO_MAX_INTAKE_SPEED);
	    RobotMap.CARGO_MAX_OUTTAKE_SPEED = SmartDashboard.getNumber("Outake Speed", RobotMap.CARGO_MAX_OUTTAKE_SPEED);
        RobotMap.CARGO_SENSOR_UPPER_THRESHOLD = SmartDashboard.getNumber("Point Of No Return", RobotMap.CARGO_SENSOR_UPPER_THRESHOLD);
    }
    

    // MOTOR FUNCTIONS
    
    // motor spin outwards
    public void shoot() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_MAX_OUTTAKE_SPEED);
    }
    
    // motor spin inwards
    public void intake() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_MAX_INTAKE_SPEED);
    }

    // Retains the Cargo within Intake while robot is in transit via proportional feedback control 
    public void retainCargo() {

        // Feedback-control—derived percentage value
	    double motorSpeed = getDistanceToCargo() * RobotMap.kP;
        
        double distance = getDistanceToCargo();
        // if within target range for Cargo distance from Infrared
        if(distance <= RobotMap.CARGO_SENSOR_UPPER_THRESHOLD && distance >= RobotMap.CARGO_SENSOR_LOWER_THRESHOLD) {
           intakeMotor.set(ControlMode.PercentOutput, motorSpeed);
        }
    }
    public static double interpLinear(double[] xVolt, double[] yDist, double newVolt) {
        
        if(newVolt <= xVolt[0])
        {
            double slope = (yDist[1] - yDist[0]) / (xVolt[1] - xVolt[0]);
            double yInt = yDist[0] - xVolt[0] * slope;
            return newVolt * slope + yInt;
        }
        
        else if(newVolt >= xVolt[xVolt.length-1])
        {
            double slope = (yDist[xVolt.length-1] - yDist[xVolt.length-1]) / (xVolt[xVolt.length-1] - xVolt[xVolt.length-1]);
            double yInt = yDist[xVolt.length-1] - xVolt[xVolt.length-1] * slope;
            return newVolt * slope + yInt;
        }
        
        for(int i = 0; i < xVolt.length-1; i++) {
            double slope = (yDist[i+1] - yDist[i]) / (xVolt[i+1] - xVolt[i]);
            double yInt = yDist[i] - xVolt[i] * slope;
            return newVolt * slope + yInt;        }
        return 0.0;
    }
    
    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
