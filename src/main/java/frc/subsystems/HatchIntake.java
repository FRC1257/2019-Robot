package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
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
    private double pidTime;                   // the timestamp for when the PID enters the tolerance range
    private boolean running;                  // whether or not the PID loop is currently running

    public HatchIntake() {
        pickupSolenoid = new Solenoid(RobotMap.HATCH_PICKUP_ID);
        ejectSolenoid = new Solenoid(RobotMap.HATCH_EJECT_ID);

        hatchPivotMotor = new CANSparkMax(RobotMap.HATCH_PIVOT_MOTOR_ID, MotorType.kBrushless);
        hatchPivotEncoder = hatchPivotMotor.getEncoder();
        hatchPivotPID = hatchPivotMotor.getPIDController();
        hatchPivotPID.setP(RobotMap.HATCH_PID_P);
        hatchPivotPID.setI(RobotMap.HATCH_PID_I);
        hatchPivotPID.setD(RobotMap.HATCH_PID_D);
        hatchPivotPID.setFF(RobotMap.HATCH_PID_F);

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

    public void reset() {
        pickupSolenoid.set(false);
        ejectSolenoid.set(false);
        hatchPivotMotor.set(0);

        currentPIDSetpoint = 0;
        pidTime = -1;
        running = false;
    }
    
    public void setPIDPosition(double value) {
        hatchPivotPID.setReference(value, ControlType.kPosition);
        currentPIDSetpoint = value;
        notifier.startPeriodic(RobotMap.HATCH_PID_UPDATE_PERIOD);
    }

    private void updatePID() {
        running = true;

        // Check if the pivot's position is within the tolerance
        if(Math.abs(getEncoderPosition() - currentPIDSetpoint) < RobotMap.HATCH_PID_TOLERANCE) {
            // If this is the first time it has been detected, then update the timestamp
            if(pidTime == -1) {
                pidTime = Timer.getFPGATimestamp();
            }

            // Check if the pivot's position has been inside the tolerance for long enough
            if((Timer.getFPGATimestamp() - pidTime) >= RobotMap.HATCH_PID_TIME) {
                notifier.stop();
                running = false;
            }
        }
        else {
            pidTime = -1;
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
