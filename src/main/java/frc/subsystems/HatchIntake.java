package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class HatchIntake {
    
    private static HatchIntake instance = null;
    DoubleSolenoid pickupSolenoid;
    DoubleSolenoid ejectSolenoid;
    private CANSparkMax hatchPivotMotor;

    public HatchIntake() {
        pickupSolenoid = new DoubleSolenoid(1, 2);
        ejectSolenoid = new DoubleSolenoid(3, 4);
        hatchPivotMotor = new CANSparkMax(RobotMap.MOTORS[0], MotorType.kBrushless);

        hatchPivotMotor.getPID();

    }
    
    // public void pickup(boolean loadingStation) {
    // }

    public void extend(type_solenoid) {
        type_solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void retract(type_solenoid) {
        type_solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void hatchPivot(speed) {
        hatchPivotMotor.set(speed);
    }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
