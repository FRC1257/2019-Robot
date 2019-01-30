package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
                                                
public class IntakeArm { 
    private static IntakeArm instance = null;

    private CANSparkMax intakeArmMotor;
    private CANEncoder intakeArmEncoder; 

    private DigitalInput limitSwitch;

    public IntakeArm() {
        intakeArmMotor = new CANSparkMax(RobotMap.INTAKE_ARM_MOTOR_PORT, MotorType.kBrushless);
        intakeArmEncoder = intakeArmMotor.getEncoder();

        limitSwitch = new DigitalInput(1);
    }

    // dictates the arm to move up or down when a specific button on the user's controller is pressed
    public void setSpeed(double speed) {
        double adjustedSpeed = speed;
        if(speed > 0 && getEncoderValue() >= 5) {
            adjustedSpeed = 0;
        }
        if(speed < 0 && getEncoderValue() <= 0) {
            adjustedSpeed = 0;
        }
        intakeArmMotor.set(adjustedSpeed);
    }

    // returns the change in distance since the last reset as scaled by the value from setDistancePerPulse()
    public double getEncoderValue() {
        return intakeArmEncoder.getPosition();
    }

    public void resetEncoder() {
        // Reset Encoder (TODO when Spark MAX API is updated)
    }

    public boolean getLimitSwitch() {
        return limitSwitch.get();
    }

    public static IntakeArm getInstance() {
        if (instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }
}
