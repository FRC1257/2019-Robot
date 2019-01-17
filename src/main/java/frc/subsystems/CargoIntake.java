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
    private double getDistanceToCargo() {
        double voltage = cargoInfrared.getAverageVoltage();
        return voltage*(2.6/-70);//Not correct equation
    }

    public void pointOfNoReturn(XboxController controller) {
        //4 in wheel, max speed 24 ft/s
        if(getDistanceToCargo() <= RobotMap.CARGO_PONR && !controller.getAButton()) { // if within target range and not pressing eject
            double speed = RobotMap.INTAKE_SPEED * getDistanceToCargo() / RobotMap.kP;
            intakeMotor.set(speed); // set to (intake speed * current distance / constant value)
        }
    }



    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
