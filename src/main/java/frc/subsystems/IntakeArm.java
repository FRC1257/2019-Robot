package frc.subsystems;

import frc.robot.RobotMap;
import frc.util.SnailSparkMaxPIDF;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

public class IntakeArm {

    private static IntakeArm instance = null;

    private CANSparkMax intakeArmMotor;
    private CANEncoder intakeArmEncoder;

    private DigitalInput limitSwitch;

    private int currentPositionState; // 0 - ground, 1 - rocket, 2 - cargo ship

    private SnailSparkMaxPIDF pidHandler;

    private IntakeArm() {
        intakeArmMotor = new CANSparkMax(RobotMap.INTAKE_ARM_MOTOR_ID, MotorType.kBrushless);
        intakeArmMotor.setIdleMode(IdleMode.kBrake);
        intakeArmMotor.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);
        intakeArmEncoder = intakeArmMotor.getEncoder();
        
        limitSwitch = new DigitalInput(RobotMap.INTAKE_ARM_LIMIT_SWITCH_ID);

        pidHandler = new SnailSparkMaxPIDF(intakeArmMotor, RobotMap.INTAKE_ARM_PIDF, RobotMap.INTAKE_ARM_PID_UPDATE_PERIOD, 
            RobotMap.INTAKE_ARM_PID_TOLERANCE, RobotMap.INTAKE_ARM_PID_TIME);

        reset();
    }

    public void reset() {
        intakeArmMotor.set(0);
        currentPositionState = 0;
        pidHandler.reset();
        resetEncoderTop();
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
        if(adjustedSpeed != 0.0 || intakeArmMotor.get() != 0.0) intakeArmMotor.set(adjustedSpeed);
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
        pidHandler.setPIDPosition(RobotMap.INTAKE_ARM_PID_GROUND);
    }

    public void moveRocket() {
        pidHandler.setPIDPosition(RobotMap.INTAKE_ARM_PID_ROCKET);
    }

    public void moveCargo() {
        pidHandler.setPIDPosition(RobotMap.INTAKE_ARM_PID_CARGO);
    }

    public void moveRaised() {
        pidHandler.setPIDPosition(RobotMap.INTAKE_ARM_PID_RAISED);
    }

    public void breakPID() {
        pidHandler.breakPID();
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
        return pidHandler.isRunning();
    }

    // Output values to Smart Dashboard
    public void outputValues() {
        SmartDashboard.putNumber("Intake Arm Position State", getPositionState());
        SmartDashboard.putBoolean("Intake Arm Limit Switch", getLimitSwitch());
        SmartDashboard.putNumber("Intake Arm Position", getEncoderPosition());
        SmartDashboard.putNumber("Intake Arm Velocity", getEncoderVelocity());

        pidHandler.outputValues("Intake Arm ");
    }

    // Initialize constants in Smart Dashboard
    public void setConstantTuning() {
        SmartDashboard.putNumber("Intake Arm Max Speed", RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED);

        pidHandler.setConstantTuning("Intake Arm ", RobotMap.INTAKE_ARM_PIDF);
    }

    // Update constants from Smart Dashboard
    public void getConstantTuning() {
        RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED = SmartDashboard.getNumber("Intake Arm Max Speed",
                RobotMap.INTAKE_ARM_MOTOR_MAX_SPEED);

        pidHandler.getConstantTuning("Intake Arm ", RobotMap.INTAKE_ARM_PIDF);
    }

    public static IntakeArm getInstance() {
        if (instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }
}
