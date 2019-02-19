package frc.subsystems;

import frc.robot.RobotMap;
import frc.util.SnailSolenoid;
import frc.util.SnailSparkMaxPIDF;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;

public class HatchIntake {

    private static HatchIntake instance = null;

    private SnailSolenoid pickupSolenoid;
    private SnailSolenoid ejectSolenoid;

    private CANSparkMax hatchPivotMotor;
    private CANEncoder hatchPivotEncoder;

    private DigitalInput limitSwitchPivot;
    private DigitalInput limitSwitchHatch;
    private boolean lastLimit;

    private boolean lowered;

    private SnailSparkMaxPIDF pidHandler;

    private HatchIntake() {
        pickupSolenoid = new SnailSolenoid(RobotMap.HATCH_PICKUP_SOLENOID_ID);
        ejectSolenoid = new SnailSolenoid(RobotMap.HATCH_EJECT_SOLENOID_ID);

        hatchPivotMotor = new CANSparkMax(RobotMap.HATCH_PIVOT_MOTOR_ID, MotorType.kBrushless);
        hatchPivotMotor.setIdleMode(IdleMode.kBrake);
        hatchPivotMotor.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);
        hatchPivotEncoder = hatchPivotMotor.getEncoder();

        limitSwitchPivot = new DigitalInput(RobotMap.HATCH_LIMIT_SWITCH_PIVOT_ID);
        limitSwitchHatch = new DigitalInput(RobotMap.HATCH_LIMIT_SWITCH_HATCH_ID);
        lastLimit = true;

        pidHandler = new SnailSparkMaxPIDF(hatchPivotMotor, RobotMap.HATCH_PIDF, RobotMap.HATCH_PID_UPDATE_PERIOD, 
            RobotMap.HATCH_PID_TOLERANCE, RobotMap.HATCH_PID_TIME);        

        reset();
    }

    public void reset() {
        pickupSolenoid.set(false);
        ejectSolenoid.set(false);
        hatchPivotMotor.set(0);
        resetEncoder();
        pidHandler.reset();
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
        double adjustedSpeed = value * RobotMap.HATCH_MOTOR_MAX_SPEED;
        // // Pivot is moving up and is past the upper threshold
        // if(adjustedSpeed < 0.0 && getEncoderPosition() <= RobotMap.HATCH_PID_RAISED)
        // {
        // adjustedSpeed = 0.0;
        // }
        // // Pivot is moving down and is past the lower threshold
        // if(adjustedSpeed > 0.0 && getEncoderPosition() >= RobotMap.HATCH_PID_LOWERED)
        // {
        // adjustedSpeed = 0.0;
        // }
        if(adjustedSpeed != 0.0 || hatchPivotMotor.get() != 0.0) hatchPivotMotor.set(adjustedSpeed);
    }

    public void togglePivot() {
        if (isLowered())
            raisePivot();
        else
            lowerPivot();
    }

    public void raisePivot() {
        pidHandler.setPIDPosition(RobotMap.HATCH_PID_RAISED);
    }

    public void lowerPivot() {
        pidHandler.setPIDPosition(RobotMap.HATCH_PID_LOWERED);
    }

    public void breakPID() {
        pidHandler.breakPID();
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

    // Updates the position state for whether or not the intake is lowered
    public void updatePositionState() {
        lowered = getEncoderPosition() >= (RobotMap.HATCH_PID_LOWERED + RobotMap.HATCH_PID_RAISED) / 2.0;
        lastLimit = getLimitSwitchPivot();
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

    public boolean getPIDRunning() {
        return pidHandler.isRunning();
    }

    // Output values to Smart Dashboard
    public void outputValues() {
        SmartDashboard.putBoolean("Hatch Lowered", isLowered());
        SmartDashboard.putBoolean("Hatch Pickup Extended", getPickupExtended());
        SmartDashboard.putBoolean("Hatch Eject Extended", getEjectExtended());
        SmartDashboard.putBoolean("Hatch Pivot Limit Switch", getLimitSwitchPivot());
        SmartDashboard.putBoolean("Hatch Hatch Limit Switch", getLimitSwitchHatch());
        SmartDashboard.putNumber("Hatch Position", getEncoderPosition());
        SmartDashboard.putNumber("Hatch Velocity", getEncoderVelocity());

        pidHandler.outputValues("Hatch ");
    }

    // Initialize constants in Smart Dashboard
    public void setConstantTuning() {
        SmartDashboard.putNumber("Hatch Max Speed", RobotMap.HATCH_MOTOR_MAX_SPEED);

        pidHandler.setConstantTuning("Hatch ", RobotMap.HATCH_PIDF);
    }

    // Update constants from Smart Dashboard
    public void getConstantTuning() {
        RobotMap.HATCH_MOTOR_MAX_SPEED = SmartDashboard.getNumber("Hatch Max Speed", RobotMap.HATCH_MOTOR_MAX_SPEED);

        pidHandler.getConstantTuning("Hatch ", RobotMap.HATCH_PIDF);
    }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
