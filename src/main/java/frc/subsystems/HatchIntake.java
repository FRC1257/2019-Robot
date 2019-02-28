package frc.subsystems;

import frc.robot.RobotMap;
import frc.util.SnailSolenoid;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

public class HatchIntake {

    private static HatchIntake instance = null;

    private SnailSolenoid pickupSolenoid;
    private SnailSolenoid ejectSolenoid;

    private CANSparkMax hatchPivotMotor;
    private CANEncoder hatchPivotEncoder;
    private CANPIDController hatchPivotPID;

    private DigitalInput limitSwitchPivot;
    private DigitalInput limitSwitchHatch;
    private boolean lastLimit;

    private double currentSetpoint;
    private boolean lowered;

    private Notifier updater;

    private HatchIntake() {
        pickupSolenoid = new SnailSolenoid(RobotMap.HATCH_PICKUP_SOLENOID_ID);
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

        currentSetpoint = hatchPivotEncoder.getPosition();

        updater = new Notifier(this::update);
        updater.startPeriodic(RobotMap.HATCH_UPDATE_PERIOD);

        setConstantTuning();
        reset();
    }

    /**
     * Update position state
     * Reset encoder
     * Update PID
     * Output to Smart Dashboard
     */
    private void update() {
        lowered = getEncoderPosition() >= (RobotMap.HATCH_PID_LOWERED + RobotMap.HATCH_PID_RAISED) / 2.0;
        if (getLimitSwitchPivotPressed()) {
            resetEncoder();
        }
        lastLimit = getLimitSwitchPivot();
        hatchPivotPID.setReference(currentSetpoint, ControlType.kPosition);
        outputValues();
    }

    public void reset() {
        pickupSolenoid.set(false);
        ejectSolenoid.set(false);
        hatchPivotMotor.set(0);
        resetEncoder();
        currentSetpoint = getEncoderPosition();

        lowered = false;
    }

    public void setPickup(boolean pickup) {
        pickupSolenoid.set(pickup);
    }

    public void pickupExtend() {
        pickupSolenoid.set(true);
    }

    public void pickupRetract() {
        pickupSolenoid.set(false);
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

    public void setPivot(double value) {
        double adjustedSpeed = value * RobotMap.HATCH_MOTOR_PID_STEP;
        currentSetpoint += adjustedSpeed;
        if (currentSetpoint < RobotMap.HATCH_PID_RAISED) {
            currentSetpoint = RobotMap.HATCH_PID_RAISED;
        } else if (currentSetpoint > RobotMap.HATCH_PID_LOWERED) {
            currentSetpoint = RobotMap.HATCH_PID_LOWERED;
        }
        hatchPivotPID.setReference(currentSetpoint, ControlType.kPosition);
    }

    public void togglePivot() {
        if (isLowered()) {
            raisePivot();
        } else {
            lowerPivot();
        }
    }

    public void raisePivot() {
        currentSetpoint = RobotMap.HATCH_PID_RAISED;
        hatchPivotPID.setReference(currentSetpoint, ControlType.kPosition);
    }

    public void lowerPivot() {
        currentSetpoint = RobotMap.HATCH_PID_LOWERED;
        hatchPivotPID.setReference(currentSetpoint, ControlType.kPosition);
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
        return pickupSolenoid.get();
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
        SmartDashboard.putNumber("Hatch PID Setpoint", currentSetpoint);
        SmartDashboard.putBoolean("Hatch Pickup Extended", getPickupExtended());
        SmartDashboard.putBoolean("Hatch Eject Extended", getEjectExtended());
        SmartDashboard.putBoolean("Hatch Pivot Limit Switch", getLimitSwitchPivot());
        SmartDashboard.putBoolean("Hatch Hatch Limit Switch", getLimitSwitchHatch());
        SmartDashboard.putNumber("Hatch Position", getEncoderPosition());
        SmartDashboard.putNumber("Hatch Velocity", getEncoderVelocity());
    }

    // Initialize constants in Smart Dashboard
    public void setConstantTuning() {
        SmartDashboard.putNumber("Hatch P", RobotMap.HATCH_PIDF[0]);
        SmartDashboard.putNumber("Hatch I", RobotMap.HATCH_PIDF[1]);
        SmartDashboard.putNumber("Hatch D", RobotMap.HATCH_PIDF[2]);
        SmartDashboard.putNumber("Hatch F", RobotMap.HATCH_PIDF[3]);
    }

    // Update constants from Smart Dashboard
    public void getConstantTuning() {
        if (RobotMap.HATCH_PIDF[0] != SmartDashboard.getNumber("Hatch P", RobotMap.HATCH_PIDF[0])) {
            RobotMap.HATCH_PIDF[0] = SmartDashboard.getNumber("Hatch P", RobotMap.HATCH_PIDF[0]);
            hatchPivotPID.setP(RobotMap.HATCH_PIDF[0]);
        }
        if (RobotMap.HATCH_PIDF[1] != SmartDashboard.getNumber("Hatch I", RobotMap.HATCH_PIDF[1])) {
            RobotMap.HATCH_PIDF[1] = SmartDashboard.getNumber("Hatch I", RobotMap.HATCH_PIDF[1]);
            hatchPivotPID.setP(RobotMap.HATCH_PIDF[1]);
        }
        if (RobotMap.HATCH_PIDF[2] != SmartDashboard.getNumber("Hatch D", RobotMap.HATCH_PIDF[2])) {
            RobotMap.HATCH_PIDF[2] = SmartDashboard.getNumber("Hatch D", RobotMap.HATCH_PIDF[2]);
            hatchPivotPID.setP(RobotMap.HATCH_PIDF[2]);
        }
        if (RobotMap.HATCH_PIDF[3] != SmartDashboard.getNumber("Hatch F", RobotMap.HATCH_PIDF[3])) {
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
