package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.*;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class CargoIntake {
    private static CargoIntake instance = null;
    VictorSPX intakeMotor;
    //4 in wheel diameter
    //ideal speed twice that of robot drive speed: 12ft * 2 = 24 ft/s
    AnalogInput cargoInfrared;
    private CargoIntake() {
        intakeMotor = new VictorSPX(RobotMap.CARGO_INTAKE_PORT);
        cargoInfrared = new AnalogInput(RobotMap.CARGO_INFARED_PORT);
    }

    public void shoot() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_OUTTAKE_SPEED);
    }
    public void intake() {
        intakeMotor.set(ControlMode.PercentOutput, RobotMap.CARGO_INTAKE_SPEED);
    }
    public double getDistanceToCargo() {
        double voltage = cargoInfrared.getAverageVoltage();
        return voltage*(2.6/-70); //Not correct equation
    }

    public void pointOfNoReturn(XboxController controller) {
        if(getDistanceToCargo() <= RobotMap.CARGO_PONR && !controller.getAButton()){
            //if within target range and not pressing eject
            double speed = RobotMap.CARGO_INTAKE_SPEED * getDistanceToCargo() * RobotMap.kP;
            // set speed to ((intake speed * current distance) / constant value)
            intakeMotor.set(ControlMode.PercentOutput, speed);
        }
    }

    public static CargoIntake getInstance() {
        if (instance == null) {
            instance = new CargoIntake();
        }
        return instance;
    }
}
