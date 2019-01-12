package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class CargoIntake {
    
    private static CargoIntake instance = null;

    private CANSparkMax cargoIntakeMotor;

    private AnalogInput distanceSensor;

    private CargoIntake() {
        cargoIntakeMotor = new CANSparkMax(RobotMap.CARGO_INTAKE_MOTOR, MotorType.kBrushless);

        distanceSensor = new AnalogInput(RobotMap.CARGO_INTAKE_DISTANCE_SENSOR);
    }
    
    

    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
