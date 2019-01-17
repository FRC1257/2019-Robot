package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;
import frc.robot.RobotMap;

public class HatchIntake {
    
    private static HatchIntake instance = null;
    private Solenoid pickupSolenoid;
    private Solenoid ejectSolenoid;
    private CANSparkMax hatchPivotMotor;
    private CANPIDController hatchPivotPID;
    private DigitalInput limitSwitch;

    public HatchIntake() {
        pickupSolenoid = new Solenoid(1);   //temporary value for port #
        ejectSolenoid = new Solenoid(2);    //temporary value for port #
        hatchPivotMotor = new CANSparkMax(RobotMap.HATCH_PIVOT_MOTOR, MotorType.kBrushless);
        hatchPivotPID = hatchPivotMotor.getPIDController();
        limitSwitch = new DigitalInput(1);

        hatchPivotPID.setP(RobotMap.HATCH_P_VALUE);
        hatchPivotPID.setI(RobotMap.HATCH_I_VALUE);
        hatchPivotPID.setD(RobotMap.HATCH_D_VALUE);
        hatchPivotPID.setFF(RobotMap.HATCH_FF_VALUE);

    }

    public void pickupExtend() {
        pickupSolenoid.set(true);
    }

    public void pickupRetract() {
        pickupSolenoid.set(false);
    }

    public void ejectExtend() {
        ejectSolenoid.set(true);
    }

    public void ejectRetract() {
        ejectSolenoid.set(false);
    }

    public void automaticIntake() {
        if(limitSwitch.get()) {
            hatchPivotPID.setReference(1, ControlType.kPosition); //dummy values
            pickupExtend();
            pickupRetract();
            ejectExtend();
            ejectRetract();
            hatchPivotPID.setReference(10, ControlType.kPosition); //dummy values
        }
    }

    public void reset() {
    }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
