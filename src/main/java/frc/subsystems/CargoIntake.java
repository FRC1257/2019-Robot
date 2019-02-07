package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.ctre.phoenix.motorcontrol.*;

public class CargoIntake {
    
    private static CargoIntake instance = null;
    
    private VictorSPX intakeMotor;

    private CargoIntake() {
        intakeMotor = new VictorSPX(RobotMap.CARGO_INTAKE_MOTOR_ID);
    }

    // Motor Functions
    
    public void shoot() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_OUTTAKE_SPEED);
    }
    
    public void intake() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_INTAKE_SPEED);
    }
    
    // Smart Dashboard
    
    // Initialize constants in Smart Dashboard
    public void setConstantTuning() {
        SmartDashboard.putNumber("Cargo Intake Speed", RobotMap.CARGO_INTAKE_SPEED);
	    SmartDashboard.putNumber("Cargo Outake Speed", RobotMap.CARGO_OUTTAKE_SPEED);
    }
    
    // Update constants from Smart Dashboard
    public void getConstantTuning() {
        RobotMap.CARGO_INTAKE_SPEED = SmartDashboard.getNumber("Cargo Intake Speed", RobotMap.CARGO_INTAKE_SPEED);
	    RobotMap.CARGO_OUTTAKE_SPEED = SmartDashboard.getNumber("Cargo Outake Speed", RobotMap.CARGO_OUTTAKE_SPEED);
    }

    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
