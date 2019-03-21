package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

public class IntakeArm {

    private static IntakeArm instance = null;

    private CANSparkMax intakeArmMotor;
    private CANEncoder intakeArmEncoder;
    private CANPIDController intakeArmPID;

    private DigitalInput limitSwitch;
    private boolean lastLimit;
    
    public enum ArmPositionState {
        GROUND, ROCKET, CARGO_SHIP, RAISED;
    }

    private ArmPositionState currentPositionState;
    private double currentPIDSetpoint; // current target position of arm

    private IntakeArm() {
        intakeArmMotor = new CANSparkMax(RobotMap.INTAKE_ARM_MOTOR_ID, MotorType.kBrushless);
        intakeArmMotor.restoreFactoryDefaults();
        intakeArmMotor.setIdleMode(IdleMode.kBrake);
        intakeArmMotor.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);
        intakeArmEncoder = intakeArmMotor.getEncoder();
        intakeArmPID = intakeArmMotor.getPIDController();
        intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[0]);
        intakeArmPID.setI(RobotMap.INTAKE_ARM_PIDF[1]);
        intakeArmPID.setD(RobotMap.INTAKE_ARM_PIDF[2]);
        intakeArmPID.setFF(RobotMap.INTAKE_ARM_PIDF[3]);
        intakeArmPID.setIZone(0.0);
        intakeArmPID.setOutputRange(RobotMap.INTAKE_ARM_PID_MIN_OUTPUT, RobotMap.INTAKE_ARM_PID_MAX_OUTPUT);
        
        limitSwitch = new DigitalInput(RobotMap.INTAKE_ARM_LIMIT_SWITCH_ID);
        lastLimit = getLimitSwitch();

        reset();
    }

    public void update() {
        if (getEncoderPosition() <= (RobotMap.INTAKE_ARM_PID_GROUND + RobotMap.INTAKE_ARM_PID_ROCKET) / 2.0) {
            currentPositionState = ArmPositionState.GROUND;
        } else if (getEncoderPosition() <= (RobotMap.INTAKE_ARM_PID_ROCKET + RobotMap.INTAKE_ARM_PID_CARGO) / 2.0) {
            currentPositionState = ArmPositionState.ROCKET;
        } else if (getEncoderPosition() <= (RobotMap.INTAKE_ARM_PID_CARGO + RobotMap.INTAKE_ARM_PID_RAISED) / 2.0) {
            currentPositionState = ArmPositionState.CARGO_SHIP;
        } else {
            currentPositionState = ArmPositionState.RAISED;
        }

        if (getLimitSwitchPressed()) {
            resetEncoder();
        }

        lastLimit = getLimitSwitch();
    }

    public void reset() {
        intakeArmMotor.set(0);

        currentPIDSetpoint = -1257;
        resetEncoderTop();
        
        currentPositionState = ArmPositionState.GROUND;
    }

    public void setSpeed(double value) {
        double adjustedSpeed = value * RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED;
        if(adjustedSpeed < 0.0 && getLimitSwitch()) adjustedSpeed = 0.0; 
        if(adjustedSpeed != 0.0 || intakeArmMotor.get() != 0.0) {
            intakeArmMotor.set(adjustedSpeed);
            currentPIDSetpoint = -1257;
        }
    }

    public void raiseArm() {
        if (getPositionState() == ArmPositionState.GROUND)
            moveRocket();
        else if (getPositionState() == ArmPositionState.ROCKET)
            moveCargo();
        else if (getPositionState() == ArmPositionState.CARGO_SHIP)
            moveRaised();
    }

    public void lowerArm() {
        if (getPositionState() == ArmPositionState.RAISED)
            moveCargo();
        else if (getPositionState() == ArmPositionState.CARGO_SHIP)
            moveRocket();
        else if (getPositionState() == ArmPositionState.ROCKET)
            moveGround();
    }

    public void moveGround() {
        setPIDPosition(RobotMap.INTAKE_ARM_PID_GROUND);
    }

    public void moveRocket() {
        setPIDPosition(RobotMap.INTAKE_ARM_PID_ROCKET);
    }

    public void moveCargo() {
        setPIDPosition(RobotMap.INTAKE_ARM_PID_CARGO);
    }

    public void moveRaised() {
        setPIDPosition(RobotMap.INTAKE_ARM_PID_RAISED);
    }

    public void setPIDPosition(double value) {
        intakeArmPID.setIAccum(0);
        intakeArmPID.setReference(value, ControlType.kPosition);
        currentPIDSetpoint = value;
    }

    /* 
     * Resets the encoder to the bottom position
     */
    public void resetEncoder() {
        intakeArmEncoder.setPosition(0.0);
    }
    
    /* 
     * Resets the encoder to the top position
     */
    public void resetEncoderTop() {
        intakeArmEncoder.setPosition(RobotMap.INTAKE_ARM_PID_RAISED);
    }

    public double getEncoderPosition() {
        
        return intakeArmEncoder.getPosition();
    }

    public double getEncoderVelocity() {
        return intakeArmEncoder.getVelocity();
    }

    /*
     * Returns the current position state dependent on the encoder position
     */
    public ArmPositionState getPositionState() {
        return currentPositionState;
    }

    // Whether or not the bottom limit switch is pressed
    public boolean getLimitSwitch() {
        return !limitSwitch.get();
    }
    

    // Whether or not the bottom limit switch is pressed
    public boolean getLimitSwitchPressed() {
        return lastLimit != getLimitSwitch();
    }

    // Output values to Smart Dashboard
    public void outputValues() {
        SmartDashboard.putString("Intake Arm Position State", getPositionState().name());
        SmartDashboard.putNumber("Intake Arm PID Setpoint", currentPIDSetpoint);
        SmartDashboard.putBoolean("Intake Arm Limit Switch", getLimitSwitch());
        SmartDashboard.putNumber("Intake Arm Position", getEncoderPosition());
        SmartDashboard.putNumber("Intake Arm Velocity", getEncoderVelocity());

        SmartDashboard.putNumber("Intake Arm Output", intakeArmMotor.getAppliedOutput());
        SmartDashboard.putNumber("Intake Arm Current", intakeArmMotor.getOutputCurrent());
        SmartDashboard.putNumber("Intake Arm Temperature (C)", intakeArmMotor.getMotorTemperature());
    }

    // Initialize constants in Smart Dashboard
    public void setConstantTuning() {
        SmartDashboard.putNumber("Intake Arm Max Speed", RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED);

        SmartDashboard.putNumber("Intake Arm P", RobotMap.INTAKE_ARM_PIDF[0]);
        SmartDashboard.putNumber("Intake Arm I", RobotMap.INTAKE_ARM_PIDF[1]);
        SmartDashboard.putNumber("Intake Arm D", RobotMap.INTAKE_ARM_PIDF[2]);
        SmartDashboard.putNumber("Intake Arm F", RobotMap.INTAKE_ARM_PIDF[3]);
    }

    // Update constants from Smart Dashboard
    public void getConstantTuning() {
        RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED = SmartDashboard.getNumber("Intake Arm Max Speed",
                RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED);

        if(RobotMap.INTAKE_ARM_PIDF[0] != SmartDashboard.getNumber("Intake Arm P", RobotMap.INTAKE_ARM_PIDF[0])) {
            RobotMap.INTAKE_ARM_PIDF[0] = SmartDashboard.getNumber("Intake Arm P", RobotMap.INTAKE_ARM_PIDF[0]);
            intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[0]);
        }
        if(RobotMap.INTAKE_ARM_PIDF[1] != SmartDashboard.getNumber("Intake Arm I", RobotMap.INTAKE_ARM_PIDF[1])) {
            RobotMap.INTAKE_ARM_PIDF[1] = SmartDashboard.getNumber("Intake Arm I", RobotMap.INTAKE_ARM_PIDF[1]);
            intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[1]);
        }
        if(RobotMap.INTAKE_ARM_PIDF[2] != SmartDashboard.getNumber("Intake Arm D", RobotMap.INTAKE_ARM_PIDF[2])) {
            RobotMap.INTAKE_ARM_PIDF[2] = SmartDashboard.getNumber("Intake Arm D", RobotMap.INTAKE_ARM_PIDF[2]);
            intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[2]);
        }
        if(RobotMap.INTAKE_ARM_PIDF[3] != SmartDashboard.getNumber("Intake Arm F", RobotMap.INTAKE_ARM_PIDF[3])) {
            RobotMap.INTAKE_ARM_PIDF[3] = SmartDashboard.getNumber("Intake Arm F", RobotMap.INTAKE_ARM_PIDF[3]);
            intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[3]);
        }
    }

    public static IntakeArm getInstance() {
        if (instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }
}
