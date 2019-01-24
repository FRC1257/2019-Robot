package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
//import com.qualcomm.robotcore.hardware.PIDCoefficients;
                                                
public class IntakeArm { 
    private static IntakeArm instance = null;
    CANSparkMax intakeArmMotor;
    DigitalInput forwardLimitSwitch;
    DigitalInput reverseLimitSwitch;
    private CANPIDController intakeArmPID;

    public IntakeArm() {
        forwardLimitSwitch = new DigitalInput(1);
        reverseLimitSwitch = new DigitalInput(2);
        intakeArmMotor = new CANSparkMax(RobotMap.intakeArmMotorPort, MotorType.kBrushless);
        intakeArmPID = intakeArmMotor.getPIDController();
        intakeArmPID.setP(RobotMap.INTAKEARM_P_VALUE);
        intakeArmPID.setI(RobotMap.INTAKEARM_I_VALUE);
        intakeArmPID.setD(RobotMap.INTAKEARM_D_VALUE);
        intakeArmPID.setFF(RobotMap.INTAKEARM_FF_VALUE);

    }
    
    public static IntakeArm getInstance() {
        if (instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }

    // setSpeed dictates the arm to move up or down when a specific button on the user's controller is pressed
    public void setSpeed(double speed) {
        intakeArmMotor.set(speed);
    }

    // restrictMotion restricts the motion and prevents the arm from moving too far up or down via limit switches 
    public void restrictMotion(){
        if (forwardLimitSwitch.get()) {
            intakeArmMotor.set(0);
        }
        else if (reverseLimitSwitch.get()) {
            intakeArmMotor.set(0);
        }
    }
    
    public void PID() {
        intakeArmPID.setReference(10, ControlType.kPosition);
        //figure out how to make it move up and down. 
    }
}
