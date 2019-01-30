package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
                                                
public class IntakeArm { 
    private static IntakeArm instance = null;
    private CANSparkMax intakeArmMotor;
    private CANEncoder intakeArmEncoder; 
    private CANPIDController intakeArmPID; 
    private DigitalInput bottomLimitSwitch;


    public IntakeArm() {
        bottomLimitSwitch = new DigitalInput(1);
        intakeArmMotor = new CANSparkMax(RobotMap.INTAKE_ARM_MOTOR_PORT, MotorType.kBrushless);
        // intakeArmEncoder = intakeArmEncoder.getEncoder();
        intakeArmPID = intakeArmMotor.getPIDController();
        intakeArmPID.setP(RobotMap.INTAKEARM_PID_P);
        intakeArmPID.setI(RobotMap.INTAKEARM_PID_I);
        intakeArmPID.setD(RobotMap.INTAKEARM_PID_D);
        intakeArmPID.setFF(RobotMap.INTAKEARM_PID_FF);

    }

    public void resetEncoder() {

    }

    // setSpeed dictates the arm to move up or down when a specific button on the user's controller is pressed
    public void setSpeed(double speed) {
        intakeArmMotor.set(speed);
    }

    // restrictMotion restricts the motion and prevents the arm from moving too far up or down via limit switches 
    public void restrictMotion(){
        if (intakeArmEncoder.getPosition() == 5) {
            intakeArmMotor.set(0);
        }
        if (bottomLimitSwitch.get()) {
            intakeArmMotor.set(0);
            resetEncoder(); 
        }
    }
    
    public void PID() {
        intakeArmPID.setReference(10, ControlType.kPosition);
        //figure out how to make it move up and down
    }

    public static IntakeArm getInstance() {
        if (instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }
}
