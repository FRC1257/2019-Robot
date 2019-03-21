package frc.subsystems;

import frc.robot.RobotMap;
import frc.util.SnailSolenoid;
import frc.util.SnailDoubleSolenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

public class HatchIntake {

    private static HatchIntake instance = null;

    private SnailDoubleSolenoid pickupSolenoid;
    private SnailSolenoid ejectSolenoid;

    private CANSparkMax hatchPivotMotor;
    private CANEncoder hatchPivotEncoder;
    private CANPIDController hatchPivotPID;

    private DigitalInput limitSwitchPivot;
    private DigitalInput limitSwitchHatch;
    private boolean lastLimit;

    private boolean lowered;
    private double currentPIDSetpoint; // current target position of pivot

    private HatchIntake() {
        pickupSolenoid = new SnailDoubleSolenoid(RobotMap.HATCH_PICKUP_SOLENOID_ID_FORWARD, RobotMap.HATCH_PICKUP_SOLENOID_ID_REVERSED);
        ejectSolenoid = new SnailSolenoid(RobotMap.HATCH_EJECT_SOLENOID_ID);

        hatchPivotMotor = new CANSparkMax(RobotMap.HATCH_PIVOT_MOTOR_ID, MotorType.kBrushless);
        hatchPivotMotor.setIdleMode(IdleMode.kBrake);
        hatchPivotMotor.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);
        hatchPivotEncoder = hatchPivotMotor.getEncoder();
        hatchPivotPID = hatchPivotMotor.getPIDController();
        hatchPivotPID.setP(RobotMap.HATCH_PIDF[0]);
        hatchPivotPID.setI(RobotMap.HATCH_PIDF[1]);
        hatchPivotPID.setD(RobotMap.HATCH_PIDF[2]);
        hatchPivotPID.setFF(RobotMap.HATCH_PIDF[3]);
        hatchPivotPID.setIZone(0.0);
        hatchPivotPID.setOutputRange(RobotMap.HATCH_PID_MIN_OUTPUT, RobotMap.HATCH_PID_MAX_OUTPUT);

        limitSwitchPivot = new DigitalInput(RobotMap.HATCH_LIMIT_SWITCH_PIVOT_ID);
        limitSwitchHatch = new DigitalInput(RobotMap.HATCH_LIMIT_SWITCH_HATCH_ID);
        lastLimit = true;

        setConstantTuning();

        reset();
    }

    public void update() {
        lowered = getEncoderPosition() >= (RobotMap.HATCH_PID_LOWERED + RobotMap.HATCH_PID_RAISED) / 2.0;

        if (getLimitSwitchPivotPressed()) {
            resetEncoder();
        }

        lastLimit = getLimitSwitchPivot();
    }

    public void reset() {
        pickupRetract();
        ejectRetract();
        hatchPivotMotor.set(0);

        currentPIDSetpoint = -1257;
        resetEncoder();

        lowered = false;
    }

    public void setPivot(double value) {
        double adjustedSpeed = value * RobotMap.HATCH_MOTOR_MAX_SPEED;
        if(adjustedSpeed != 0.0 || hatchPivotMotor.get() != 0.0) {
            hatchPivotMotor.set(adjustedSpeed);
            currentPIDSetpoint = -1257;
        }
    }
    
    public void togglePivot() {
        if (isLowered())
            raisePivot();
        else
            lowerPivot();
    }

    public void raisePivot() {
        setPIDPosition(RobotMap.HATCH_PID_RAISED);
    }

    public void lowerPivot() {
        setPIDPosition(RobotMap.HATCH_PID_LOWERED);
    }

    public void setPickup(boolean pickup) {
        if(pickup) {
            pickupExtend();
        }
        else {
            pickupRetract();
        }
    }

    public void setPickup(Value pickup) {
        pickupSolenoid.set(pickup);
    }

    public void pickupExtend() {
        pickupSolenoid.set(Value.kReverse);
    }

    public void pickupRetract() {
        pickupSolenoid.set(Value.kForward);
    }

    public void setEject(boolean eject) {
        ejectSolenoid.set(eject);
    }

    public void ejectExtend() {
        ejectSolenoid.set(true);
    }

    public void ejectRetract() {
        ejectSolenoid.set(false);
    }

    public void setPIDPosition(double value) {
        hatchPivotPID.setIAccum(0);
        hatchPivotPID.setReference(value, ControlType.kPosition);
        currentPIDSetpoint = value;
    }

    public void resetEncoder() {
        hatchPivotEncoder.setPosition(0.0);
    }

    public double getEncoderPosition() {
        return hatchPivotEncoder.getPosition();
    }

    public double getEncoderVelocity() {
        return hatchPivotEncoder.getVelocity();
    }

    public boolean isLowered() {
        return lowered;
    }

    public boolean getPickupExtended() {
        return pickupSolenoid.get() == Value.kReverse;
    }

    public boolean getEjectExtended() {
        return ejectSolenoid.get();
    }

    public boolean getLimitSwitchPivot() {
        return !limitSwitchPivot.get();
    }

    public boolean getLimitSwitchHatch() {
        return !limitSwitchHatch.get();
    }

    // Returns when the limit switch is first pressed or first released
    public boolean getLimitSwitchPivotPressed() {
        return lastLimit != getLimitSwitchPivot();
    }

    // Output values to Smart Dashboard
    public void outputValues() {
        SmartDashboard.putBoolean("Hatch Lowered", isLowered());
        SmartDashboard.putNumber("Hatch Setpoint", currentPIDSetpoint);
        SmartDashboard.putBoolean("Hatch Pickup Extended", getPickupExtended());
        SmartDashboard.putBoolean("Hatch Eject Extended", getEjectExtended());
        SmartDashboard.putBoolean("Hatch Pivot Limit Switch", getLimitSwitchPivot());
        SmartDashboard.putBoolean("Hatch Hatch Limit Switch", getLimitSwitchHatch());
        SmartDashboard.putNumber("Hatch Position", getEncoderPosition());
        SmartDashboard.putNumber("Hatch Velocity", getEncoderVelocity());

        SmartDashboard.putNumber("Hatch Pivot Current", hatchPivotMotor.getOutputCurrent());
    }

    // Initialize constants in Smart Dashboard
    public void setConstantTuning() {
        SmartDashboard.putNumber("Hatch Max Speed", RobotMap.HATCH_MOTOR_MAX_SPEED);

        SmartDashboard.putNumber("Hatch P", RobotMap.HATCH_PIDF[0]);
        SmartDashboard.putNumber("Hatch I", RobotMap.HATCH_PIDF[1]);
        SmartDashboard.putNumber("Hatch D", RobotMap.HATCH_PIDF[2]);
        SmartDashboard.putNumber("Hatch F", RobotMap.HATCH_PIDF[3]);
    }

    // Update constants from Smart Dashboard
    public void getConstantTuning() {
        RobotMap.HATCH_MOTOR_MAX_SPEED = SmartDashboard.getNumber("Hatch Max Speed", RobotMap.HATCH_MOTOR_MAX_SPEED);
        
        if(RobotMap.HATCH_PIDF[0] != SmartDashboard.getNumber("Hatch P", RobotMap.HATCH_PIDF[0])) {
            RobotMap.HATCH_PIDF[0] = SmartDashboard.getNumber("Hatch P", RobotMap.HATCH_PIDF[0]);
            hatchPivotPID.setP(RobotMap.HATCH_PIDF[0]);
        }
        if(RobotMap.HATCH_PIDF[1] != SmartDashboard.getNumber("Hatch I", RobotMap.HATCH_PIDF[1])) {
            RobotMap.HATCH_PIDF[1] = SmartDashboard.getNumber("Hatch I", RobotMap.HATCH_PIDF[1]);
            hatchPivotPID.setP(RobotMap.HATCH_PIDF[1]);
        }
        if(RobotMap.HATCH_PIDF[2] != SmartDashboard.getNumber("Hatch D", RobotMap.HATCH_PIDF[2])) {
            RobotMap.HATCH_PIDF[2] = SmartDashboard.getNumber("Hatch D", RobotMap.HATCH_PIDF[2]);
            hatchPivotPID.setP(RobotMap.HATCH_PIDF[2]);
        }
        if(RobotMap.HATCH_PIDF[3] != SmartDashboard.getNumber("Hatch F", RobotMap.HATCH_PIDF[3])) {
            RobotMap.HATCH_PIDF[3] = SmartDashboard.getNumber("Hatch F", RobotMap.HATCH_PIDF[3]);
            hatchPivotPID.setP(RobotMap.HATCH_PIDF[3]);
        }
    }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
