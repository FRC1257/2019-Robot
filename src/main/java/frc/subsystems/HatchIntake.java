package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Notifier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

public class HatchIntake {
    
    private static HatchIntake instance = null;

    private Solenoid pickupSolenoid;
    private Solenoid ejectSolenoid;

    private CANSparkMax hatchPivotMotor;
    private CANEncoder hatchPivotEncoder;
    private CANPIDController hatchPivotPID;

    private DigitalInput limitSwitch;

    private Notifier notifier;                // looper for updating PID loop for pivot
    private double currentPIDSetpoint;        // current target position of pivot
    private int count;                        // the amount of consecutive times the PID loop has been inside tolerance range
    private boolean running;                  // whether or not the notifier is currently running

    public HatchIntake() {
        pickupSolenoid = new Solenoid(RobotMap.HATCH_PICKUP_ID);
        ejectSolenoid = new Solenoid(RobotMap.HATCH_EJECT_ID);

        hatchPivotMotor = new CANSparkMax(RobotMap.HATCH_PIVOT_MOTOR_ID, MotorType.kBrushless);
        hatchPivotEncoder = hatchPivotMotor.getEncoder();
        hatchPivotPID = hatchPivotMotor.getPIDController();
        hatchPivotPID.setP(RobotMap.HATCH_PID_P_VALUE);
        hatchPivotPID.setI(RobotMap.HATCH_PID_I_VALUE);
        hatchPivotPID.setD(RobotMap.HATCH_PID_D_VALUE);
        hatchPivotPID.setFF(RobotMap.HATCH_PID_FF_VALUE);

        limitSwitch = new DigitalInput(RobotMap.HATCH_LIMIT_ID);

        notifier = new Notifier(this::updatePID);
        
        reset();
    }

    public void setPickup(boolean pickup){
        pickupSolenoid.set(pickup);
    }

    public void pickupExtend() {
        pickupSolenoid.set(true);
    }

    public void pickupRetract() {
        pickupSolenoid.set(false);
    }
    
    public void setEject(boolean eject){
        ejectSolenoid.set(eject);
    }

    public void ejectExtend() {
        ejectSolenoid.set(true);
    }

    public void ejectRetract() {
        ejectSolenoid.set(false);
    }

    public void setPIDPosition(double value) {
        hatchPivotPID.setReference(value, ControlType.kPosition);
        currentPIDSetpoint = value;
        notifier.startPeriodic(RobotMap.HATCH_PID_UPDATE_PERIOD);
    }

    public void reset() {
        pickupSolenoid.set(false);
        ejectSolenoid.set(false);
        hatchPivotMotor.set(0);

        currentPIDSetpoint = 0;
        count = 0;
        running = false;
    }

    private void updatePID() {
        running = true;
        if(Math.abs(getEncoderPosition() - currentPIDSetpoint) < RobotMap.HATCH_PID_TOLERANCE) {
            count++;
        }
        else {
            count = 0;
        }

        if(count >= RobotMap.HATCH_PID_COUNT) {
            notifier.stop();
            running = false;
        }
    }

    public double getEncoderPosition() {
        return hatchPivotEncoder.getPosition();
    }

    public double getEncoderVelocity() {
        return hatchPivotEncoder.getVelocity();
    }

    public boolean getLimitSwitch() {
        return limitSwitch.get();
    }

    public boolean getPIDRunning() {
        return running;
    }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
