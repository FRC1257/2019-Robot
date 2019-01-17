package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
                                                
public class IntakeArm { 
    private static IntakeArm instance = null;
    CANSparkMax intakeArmMotor;
    DigitalInput forwardLimitSwitch;
    DigitalInput reverseLimitSwitch;

    public IntakeArm() {
        forwardLimitSwitch = new DigitalInput(1);
        reverseLimitSwitch = new DigitalInput(2);
        intakeArmMotor = new CANSparkMax(RobotMap.intakeArmMotorPort, MotorType.kBrushless);
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
    /*
    public void PID() {
        c = pid(Kp, Ki, Kd);
    }
    */
}
