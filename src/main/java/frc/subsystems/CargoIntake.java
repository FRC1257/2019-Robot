package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class CargoIntake {
    
    private static CargoIntake instance = null;
    CANSparkMax intakeMotor; 

    private CargoIntake() {
        intakeMotor = new CANSparkMax(RobotMap.CARGO_INTAKE, MotorType.kBrushless);
    }

    public void shoot() {
        intakeMotor.set(RobotMap.OUTTAKE_SPEED);
    }

    public void intake() {
        intakeMotor.set(RobotMap.INTAKE_SPEED);
    }
    
    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
