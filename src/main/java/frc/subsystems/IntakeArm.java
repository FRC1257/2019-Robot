package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeArm {
    private static IntakeArm instance = null;
    //DigitalInput forwardLimitSwitch;
    //DigitalInput reverseLimitSwitch;
    CANSparkMax intakeArmMotor;

    public IntakeArm() {
        //forwardLimitSwitch = new DigitalInput(1);
        //reverseLimitSwitch = new DigitalInput(2);
        intakeArmMotor = new CANSparkMax(RobotMap.intakeArmMotorPort, MotorType.kBrushless);
    }

    public void IntakeArmMotionSetSpeed(double speed) {
        intakeArmMotor.set(speed);
    }

    public static IntakeArm getInstance() {
        if (instance == null) {
            instance = new IntakeArm();
        }
        return instance;
    }

    /*
    if ((controller.getBumper(GenericHID.Hand.kRight)) && !(forwardLimitSwitch.get())) {
        // forwardLimitSwitch is the switch that is pressed when the arm reaches its upper limit
        IntakeArmMotor.set(1);
    }
    else if ((controller.getBumper(GenericHID.Hand.kLeft)) && !(reverseLimitSwitch.get())) {
        // reverseLimitSwitch is the switch that is pressed when the arm reaches its lower limit
        IntakeArmMotor.set(-1);
    }
    
    public void PID() {
        if (!IntakeArmPIDController.isEnabled()) {
            IntakeArmMotor.set();
        }
        IntakeArmPIDController.enable();
    }
    */
}
