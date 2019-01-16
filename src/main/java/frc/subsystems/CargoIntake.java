package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class CargoIntake {
    
    private static CargoIntake instance = null;
    CANSparkMax intakeMotor; 
    AnalogInput cargoInfrared;

    private CargoIntake() {
        intakeMotor = new CANSparkMax(RobotMap.CARGO_INTAKE_PORT, MotorType.kBrushless);
        cargoInfrared = new AnalogInput(RobotMap.CARGO_INFARED_PORT);
    }

    public void shoot() {
        intakeMotor.set(RobotMap.OUTTAKE_SPEED);
    }

    public void intake() {
        intakeMotor.set(RobotMap.INTAKE_SPEED);
    }
    public void pointOfNoReturn() {
        
    }

    public double voltToCm(double voltage) {
        // return voltage * ___;
    }
    
    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
