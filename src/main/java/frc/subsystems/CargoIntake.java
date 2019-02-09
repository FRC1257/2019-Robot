package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class CargoIntake {
    
    private static CargoIntake instance = null;
    
    private WPI_VictorSPX intakeMotor;

    private CargoIntake() {
        intakeMotor = new WPI_VictorSPX(RobotMap.CARGO_INTAKE_MOTOR_ID);
        intakeMotor.setNeutralMode(NeutralMode.Brake);
    }

    // Motor Functions
    public void shoot() {
        intakeMotor.set(RobotMap.CARGO_OUTTAKE_SPEED);
    }
    
    public void intake() {
        intakeMotor.set(RobotMap.CARGO_INTAKE_SPEED);
    }

    public void stop() {
        intakeMotor.set(0);
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
