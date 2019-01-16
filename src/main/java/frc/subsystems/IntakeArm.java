package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeArm {
    // 1 NEO motor with 1 Spark MAX

    private static IntakeArm instance = null;
    //DigitalInput forwardLimitSwitch;
    //DigitalInput reverseLimitSwitch;
    WPI_TalonSRX IntakeArmMotor;

    public IntakeArm() {
        //forwardLimitSwitch = new DigitalInput(1);
        //reverseLimitSwitch = new DigitalInput(2);
        IntakeArmMotor = new WPI_TalonSRX(1);
    }

    public void IntakeArmMotionSetSpeed(double speed) {
        IntakeArmMotor.set(speed);
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
