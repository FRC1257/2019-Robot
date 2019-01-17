package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;
import frc.robot.RobotMap;

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

        hatchPivotPID.setP(RobotMap.HATCH_P_VALUE);
        hatchPivotPID.setI(RobotMap.HATCH_I_VALUE);
        hatchPivotPID.setD(RobotMap.HATCH_D_VALUE);
        hatchPivotPID.setFF(RobotMap.HATCH_FF_VALUE);

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
