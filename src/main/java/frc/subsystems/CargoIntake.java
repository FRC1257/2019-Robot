package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class CargoIntake {
    
    private static CargoIntake instance = null;
    WPI_TalonSRX intakeMotor;

    private CargoIntake() {
        intakeMotor = new WPI_TalonSRX(5);
    }
    public void setOuttake() {
        intakeMotor.set(RobotMap.OUTTAKE_SPEED);
    }
    public void setIntake() {
        intakeMotor.set(RobotMap.INTAKE_SPEED);
    }
    
    // Shoot when hit left trigger
    

    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
