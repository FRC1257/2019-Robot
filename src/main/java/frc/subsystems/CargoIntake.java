package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class CargoIntake {
    
    // Singleton instance flag; used with Singleton static method (bottommost function)
    private static CargoIntake instance = null;
    
    // Variables Allocated for Member Objects & Global Variables
    private AnalogInput cargoInfrared;
    private VictorSPX intakeMotor;
        // 4 in wheel diameter

    // constructor
    private CargoIntake() {
        // Instantiates relevant Member Objects
        intakeMotor = new VictorSPX(RobotMap.CARGO_INTAKE_PORT);
        cargoInfrared = new AnalogInput(RobotMap.CARGO_INFARED_PORT);
	

    }
    
    
    
    // Infrared analog voltage >> Distance in centimeters
    public double getDistanceToCargo() {
        return cargoInfrared.getAverageVoltage()*RobotMap.CARGO_INFRARED_CONVERSION_FACTOR;
    }
    
    
    // SMART DASHBOARD
    
    // Diagnostic Data to  continually pushed to Shuffle Board during teleopPeriodic
    public void telemetry() {
        SmartDashboard.putNumber("Distance to Cargo", getDistanceToCargo());
        SmartDashboard.putNumber("Voltage Recieved from Analog Sensor", cargoInfrared.getAverageVoltage());
       
    }
    
    // Constants initialized in Shuffle Board during robotInit 
    public void setConstantTuning() {
        SmartDashboard.putNumber("Intake Speed", RobotMap.CARGO_INTAKE_SPEED);
	    SmartDashboard.putNumber("Outake Speed", RobotMap.CARGO_OUTTAKE_SPEED);
        SmartDashboard.putNumber("Point Of No Return", RobotMap.CARGO_SENSOR_UPPER_THRESHOLD);
    }
    
    // Updated Constants to be continually read from Shuffle Board during teleopPeriodic 
    public void getConstantTuning() {
        RobotMap.CARGO_INTAKE_SPEED = SmartDashboard.getNumber("Intake Speed", RobotMap.CARGO_INTAKE_SPEED);
	    RobotMap.CARGO_OUTTAKE_SPEED = SmartDashboard.getNumber("Outake Speed", RobotMap.CARGO_OUTTAKE_SPEED);
        RobotMap.CARGO_SENSOR_UPPER_THRESHOLD = SmartDashboard.getNumber("Point Of No Return", RobotMap.CARGO_SENSOR_UPPER_THRESHOLD);
    }
    

    // MOTOR FUNCTIONS
    
    // motor spin outwards
    public void shoot() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_OUTTAKE_SPEED);
    }
    
    // motor spin inwards
    public void intake() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_INTAKE_SPEED);
    }

    // Retains the Cargo within Intake while robot is in transit via proportional feedback control 
    public void retainCargo() {
        // if within target range for Cargo distance from Infrared

        // Feedback-control—derived percentage value
	    double motorSpeed = getDistanceToCargo() * RobotMap.kP;
	    // ((intake speed * current distance) / upper limit)
        
        double distance = getDistanceToCargo();
        if(distance <= RobotMap.CARGO_SENSOR_UPPER_THRESHOLD && distance >= RobotMap.CARGO_SENSOR_LOWER_THRESHOLD) {
           // set motor to proportional value
           intakeMotor.set(ControlMode.PercentOutput, motorSpeed);
        }
    }
    
    
    // singleton static method
    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
