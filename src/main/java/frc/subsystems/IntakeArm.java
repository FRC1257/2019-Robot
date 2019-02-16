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

    private int currentPositionState; // 0 - ground, 1 - rocket, 2 - cargo ship

    private Notifier notifier; // looper for updating PID loop for arm
    private double currentPIDSetpoint; // current target position of arm
    private double pidTime; // the timestamp for when the PID enters the tolerance range
    private boolean running; // whether or not the PID loop is currently running

    public IntakeArm() {
        intakeArmMotor = new CANSparkMax(RobotMap.INTAKE_ARM_MOTOR_ID, MotorType.kBrushless);
        intakeArmMotor.setIdleMode(IdleMode.kBrake);
        intakeArmEncoder = intakeArmMotor.getEncoder();
        limitSwitch = new DigitalInput(RobotMap.INTAKE_ARM_LIMIT_SWITCH_ID);

        intakeArmPID = intakeArmMotor.getPIDController();
        intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[0]);
        intakeArmPID.setI(RobotMap.INTAKE_ARM_PIDF[1]);
        intakeArmPID.setD(RobotMap.INTAKE_ARM_PIDF[2]);
        intakeArmPID.setFF(RobotMap.INTAKE_ARM_PIDF[3]);
        intakeArmPID.setOutputRange(RobotMap.INTAKE_ARM_PID_MIN_OUTPUT, RobotMap.INTAKE_ARM_PID_MAX_OUTPUT);

        notifier = new Notifier(this::updatePID);

        reset();
    }

    public void reset() {
        intakeArmMotor.set(0);

        currentPositionState = 0;
        currentPIDSetpoint = 0;
        pidTime = -1;
        running = false;
        resetEncoder();
    }

    // Moves the arm at a set speed and restricts the motion of the arm
    public void setSpeed(double value) {
        double adjustedSpeed = value * RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED;
        // // Arm is moving up and is past the upper threshold
        // if(speed > 0.0 && getEncoderPosition() >=
        // RobotMap.INTAKE_ARM_UPPER_THRESHOLD) {
        // adjustedSpeed = 0.0;
        // }
        // // Arm is moving down and is past the lower threshold
        // if(speed < 0.0 && getEncoderPosition() <=
        // RobotMap.INTAKE_ARM_LOWER_THRESHOLD) {
        // adjustedSpeed = 0.0;
        // }
        intakeArmMotor.set(adjustedSpeed);
    }

    public void raiseArm() {
        if (getPositionState() == 0)
            moveRocket();
        else if (getPositionState() == 1)
            moveCargo();
        else if (getPositionState() == 2)
            moveRaised();
    }

    public void lowerArm() {
        if (getPositionState() == 3)
            moveCargo();
        else if (getPositionState() == 2)
            moveRocket();
        else if (getPositionState() == 1)
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
        intakeArmPID.setReference(value, ControlType.kPosition);
        currentPIDSetpoint = value;
        intakeArmPID.setIAccum(0);
        notifier.startPeriodic(RobotMap.INTAKE_ARM_PID_UPDATE_PERIOD);
    }

    private void updatePID() {
        running = true;
        intakeArmPID.setReference(currentPIDSetpoint, ControlType.kPosition);
        System.out.println("Arm going to " + currentPIDSetpoint + 
            " and currently at " + intakeArmEncoder.getPosition());

        // Check if the encoder's position is within the tolerance
        if (Math.abs(getEncoderPosition() - currentPIDSetpoint) < RobotMap.INTAKE_ARM_PID_TOLERANCE) {
            // If this is the first time it has been detected, then update the timestamp
            if (pidTime == -1) {
                pidTime = Timer.getFPGATimestamp();
            }

            // Check if the encoder's position has been inside the tolerance for long enough
            if ((Timer.getFPGATimestamp() - pidTime) >= RobotMap.INTAKE_ARM_PID_TIME) {
                breakPID();
            }
        } else {
            pidTime = -1;
        }
    }

    public void breakPID() {
        notifier.stop();
        running = false;
        pidTime = -1;
    }

    public void resetEncoder() {
        intakeArmEncoder.setPosition(0.0);
    }

    public double getEncoderPosition() {
        return intakeArmEncoder.getPosition();
    }

    public double getEncoderVelocity() {
        return intakeArmEncoder.getVelocity();
    }

    /*
     * Sets the current position state dependent on the encoder position 0 - ground,
     * 1 - rocket, 2 - cargo, 3 - raised
     */
    public void updatePositionState() {
        if (getEncoderPosition() <= (RobotMap.INTAKE_ARM_PID_GROUND + RobotMap.INTAKE_ARM_PID_ROCKET) / 2.0) {
            currentPositionState = 0;
        } else if (getEncoderPosition() <= (RobotMap.INTAKE_ARM_PID_ROCKET + RobotMap.INTAKE_ARM_PID_CARGO) / 2.0) {
            currentPositionState = 1;
        } else if (getEncoderPosition() <= (RobotMap.INTAKE_ARM_PID_CARGO + RobotMap.INTAKE_ARM_PID_RAISED) / 2.0) {
            currentPositionState = 2;
        } else {
            currentPositionState = 3;
        }
    }

    /*
     * Returns the current position state dependent on the encoder position 0 -
     * ground, 1 - rocket, 2 - cargo, 3 - raised
     */
    public int getPositionState() {
        return currentPositionState;
    }

    // Whether or not the bottom limit switch is pressed
    public boolean getLimitSwitch() {
        return !limitSwitch.get();
    }

    public boolean getPIDRunning() {
        return running;
    }

    // Output values to Smart Dashboard
    public void outputValues() {
        SmartDashboard.putNumber("Intake Arm Position State", getPositionState());
        SmartDashboard.putBoolean("Intake Arm PID Active", running);
        SmartDashboard.putNumber("Intake Arm PID Setpoint", currentPIDSetpoint);
        SmartDashboard.putBoolean("Intake Arm Limit Switch", getLimitSwitch());
        SmartDashboard.putNumber("Intake Arm Position", getEncoderPosition());
        SmartDashboard.putNumber("Intake Arm Velocity", getEncoderVelocity());
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

        RobotMap.INTAKE_ARM_PIDF[0] = SmartDashboard.getNumber("Intake Arm P", RobotMap.INTAKE_ARM_PIDF[0]);
        intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[0]);

        RobotMap.INTAKE_ARM_PIDF[1] = SmartDashboard.getNumber("Intake Arm I", RobotMap.INTAKE_ARM_PIDF[1]);
        intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[1]);

        RobotMap.INTAKE_ARM_PIDF[2] = SmartDashboard.getNumber("Intake Arm D", RobotMap.INTAKE_ARM_PIDF[2]);
        intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[2]);

        RobotMap.INTAKE_ARM_PIDF[3] = SmartDashboard.getNumber("Intake Arm F", RobotMap.INTAKE_ARM_PIDF[3]);
        intakeArmPID.setP(RobotMap.INTAKE_ARM_PIDF[3]);
    }

    public static IntakeArm getInstance() {
        if (instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }
}
