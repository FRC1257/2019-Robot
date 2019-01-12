package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; //needed to instantie motor type

public class CargoIntake {
    private static CargoIntake instance = null;

    private CANSparkMax ciMotor;

    private AnalogInput distanceSensor;

    private CargoIntake() {
        ciMotor = new CANSparkMax(RobotMap.motors[4], MotorType.kBrushless);

        distanceSensor = new AnalogInput(RobotMap.analogInputs[0]);
    }
    
    

    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
