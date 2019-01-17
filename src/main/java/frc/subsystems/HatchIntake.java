package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class HatchIntake {
    
    private static HatchIntake instance = null;
    private Solenoid pickupSolenoid;
    private Solenoid ejectSolenoid;
    private CANSparkMax hatchPivotMotor;
    private CANPIDController hatchPivotPID;

    public HatchIntake() {
        pickupSolenoid = new Solenoid(1);   //temporary value for port #
        ejectSolenoid = new Solenoid(2);    //temporary value for port #
        hatchPivotMotor = new CANSparkMax(RobotMap.HATCH_PIVOT_MOTOR, MotorType.kBrushless);
        hatchPivotPID = hatchPivotMotor.getPIDController();

        hatchPivotPID.setD(RobotMap.P_VALUE);
        hatchPivotPID.setI(RobotMap.I_VALUE);
        hatchPivotPID.setD(RobotMap.D_VALUE);
        hatchPivotPID.setFF(RobotMap.FF_VALUE);

    }
    
    // public void pickup(boolean loadingStation) {
    // }

    public void pickupExtend() {
        pickupSolenoid.set(1);
    }

    public void pickupRetract() {
        pickupSolenoid.set(0);
    }

    public void ejectExtend() {
        ejectSolenoid.set(1);
    }

    public void ejectRetract() {
        ejectSolenoid.set(0);
    }


    public void hatchPivot(speed) {
        hatchPivotMotor.set(speed);
    }

    // public void automaticIntake() {
    //     hatchPivotMotor.set(speed)    
    // }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
