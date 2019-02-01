package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
                                                
public class IntakeArm { 
    private static IntakeArm instance = null;

    private CANSparkMax intakeArmMotor;
    private CANEncoder intakeArmEncoder; 

    private DigitalInput limitSwitch;

    private CANPIDController intakeArmPID;
    
    private Notifier notifier;                // looper for updating PID loop for arm
    private double currentPIDSetpoint;        // current target position of arm
    private double pidTime;                   // the timestamp for when the PID enters the tolerance range
    private boolean running;                  // whether or not the PID loop is currently running

    public IntakeArm() {
        intakeArmMotor = new CANSparkMax(RobotMap.INTAKE_ARM_MOTOR_PORT, MotorType.kBrushless);
        intakeArmEncoder = intakeArmMotor.getEncoder();
        limitSwitch = new DigitalInput(1);

        intakeArmPID = intakeArmMotor.getPIDController();
        intakeArmPID.setP(RobotMap.INTAKE_ARM_PID_CONSTANTS[0]);
        intakeArmPID.setI(RobotMap.INTAKE_ARM_PID_CONSTANTS[1]);
        intakeArmPID.setD(RobotMap.INTAKE_ARM_PID_CONSTANTS[2]);
        intakeArmPID.setFF(RobotMap.INTAKE_ARM_PID_CONSTANTS[3]);

        notifier = new Notifier(this::updatePID);

        reset();
    }

    public void reset() {
        currentPIDSetpoint = 0;
        pidTime = -1;
        running = false;
        resetEncoder(); // TODO
    }

    // dictates the arm to move up or down when a specific button on the user's controller is pressed
    public void setSpeed(double speed) {
        double adjustedSpeed = speed;
        if(speed > 0.0 && getEncoderPosition() >= RobotMap.TOP_TARGET_POSITION) {
            adjustedSpeed = 0.0;
        }
        if(speed < 0.0 && getEncoderPosition() <= RobotMap.BOTTOM_TARGET_POSITION) {
            adjustedSpeed = 0.0;
        }
        intakeArmMotor.set(adjustedSpeed);
    }

    // returns the change in distance since the last reset as scaled by the value from setDistancePerPulse()
    public double getEncoderPosition() {
        return intakeArmEncoder.getPosition();
    }

    public void resetEncoder() { // resets when arm hits limit switch
        // Reset Encoder (TODO when Spark MAX API is updated)
    }

    public boolean getLimitSwitch() {
        return limitSwitch.get();
    }

    // PID CONTROLLER

    public void setPIDPosition(double value) {
        intakeArmPID.setReference(value, ControlType.kPosition);
        currentPIDSetpoint = value;
        notifier.startPeriodic(RobotMap.INTAKE_ARM_PID_UPDATE_PERIOD);
    }

    private void updatePID() {
        running = true;

        // Check if the pivot's position is within the tolerance
        if(Math.abs(getEncoderPosition() - currentPIDSetpoint) < RobotMap.INTAKE_ARM_PID_TOLERANCE) {
            // If this is the first time it has been detected, then update the timestamp
            if(pidTime == -1) {
                pidTime = Timer.getFPGATimestamp();
            }

            // Check if the pivot's position has been inside the tolerance for long enough
            if((Timer.getFPGATimestamp() - pidTime) >= RobotMap.INTAKE_ARM_PID_TIME) {
                notifier.stop();
                running = false;
            }
        }
        else {
            pidTime = -1;
        }
    }

    public static IntakeArm getInstance() {
        if(instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }
}
