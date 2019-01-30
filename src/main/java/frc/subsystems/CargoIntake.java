package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CargoIntake {
    
    private static CargoIntake instance = null;
    
    private AnalogInput cargoInfrared;
    private WPI_TalonSRX intakeMotor;
    private Notifier voltNotif;

    private CargoIntake() {
        intakeMotor = new WPI_TalonSRX(RobotMap.CARGO_INTAKE_PORT);
        cargoInfrared = new AnalogInput(RobotMap.CARGO_INFARED_PORT);
        voltNotif = new Notifier(this::getVoltage);
    }
    
   
    
    // SMART DASHBOARD
    
    public void telemetry() { // diagnostic data to  continually pushed to ShuffleBoard during teleopPeriodic
        SmartDashboard.putNumber("Distance to Cargo", getDistanceToCargo());
        SmartDashboard.putNumber("Voltage Recieved from Analog Sensor", cargoInfrared.getAverageVoltage());
    }
    
    public void setConstantTuning() { // constants initialized in Shuffle Board during robotInit
        SmartDashboard.putNumber("Intake Speed", RobotMap.CARGO_MAX_INTAKE_SPEED);
	    SmartDashboard.putNumber("Outake Speed", RobotMap.CARGO_MAX_OUTTAKE_SPEED);
        SmartDashboard.putNumber("Point Of No Return", RobotMap.CARGO_SENSOR_UPPER_THRESHOLD);
    }
    
    public void getConstantTuning() { // updated constants to be continually read from ShuffleBoard during teleopPeriodic
        RobotMap.CARGO_MAX_INTAKE_SPEED = SmartDashboard.getNumber("Intake Speed", RobotMap.CARGO_MAX_INTAKE_SPEED);
	    RobotMap.CARGO_MAX_OUTTAKE_SPEED = SmartDashboard.getNumber("Outake Speed", RobotMap.CARGO_MAX_OUTTAKE_SPEED);
        RobotMap.CARGO_SENSOR_UPPER_THRESHOLD = SmartDashboard.getNumber("Point Of No Return", RobotMap.CARGO_SENSOR_UPPER_THRESHOLD);
    }
    
    // MOTOR FUNCTIONS
    
    public void shoot() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_MAX_OUTTAKE_SPEED);
    }
    
    public void intake() {
        if (!cargoInside()) {
            intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_MAX_INTAKE_SPEED);
        } else {
            intakeMotor.set(ControlMode.PercentOutput, 0.0);
        }
    }

    public boolean cargoInside() {
        double distance = getDistanceToCargo();
        if (distance <= RobotMap.CARGO_SENSOR_LOWER_THRESHOLD) { // if cargo is in intake
            return true;
        } else {
            return false;
        }
    }

    public double getVoltage() {
        return cargoInfrared.getAverageVoltage();
    }
    
    public double getDistanceToCargo() {
        double volt = getVoltage();
        return interpLinear(RobotMap.CARGO_KNOWN_VOLTAGE_PTS, RobotMap.CARGO_KNOWN_DISTANCE_PTS, volt);
        //return volt*RobotMap.CARGO_INFRARED_CONVERSION_FACTOR;
    }
    
    public static double interpLinear(double[] xVolt, double[] yDist, double newVolt) {
        if (newVolt <= xVolt[0]) {
            double slope = (yDist[1] - yDist[0]) / (xVolt[1] - xVolt[0]);
            double yInt = yDist[0] - xVolt[0] * slope;
            return newVolt * slope + yInt;
        } else if (newVolt >= xVolt[xVolt.length-1]) {
            double slope = (yDist[xVolt.length-1] - yDist[xVolt.length-1]) / (xVolt[xVolt.length-1] - xVolt[xVolt.length-1]);
            double yInt = yDist[xVolt.length-1] - xVolt[xVolt.length-1] * slope;
            return newVolt * slope + yInt;
        }

        for (int i = 1; i < xVolt.length-1; i++) {
            double slope = (yDist[i+1] - yDist[i]) / (xVolt[i+1] - xVolt[i]);
            double yInt = yDist[i] - xVolt[i] * slope;
            if (newVolt >= xVolt[i] && newVolt <= xVolt[i + 1]) {
                return newVolt * slope + yInt;
            }      
        }

        return 0.0;
    }

    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
