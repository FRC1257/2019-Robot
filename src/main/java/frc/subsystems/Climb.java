package frc.subsystems;

import frc.robot.RobotMap;
import frc.util.Gyro;
import frc.util.SnailDoubleSolenoid;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Climb {
    
    private static Climb instance = null;

    private SnailDoubleSolenoid frontSolenoid;
    private SnailDoubleSolenoid backSolenoid;

    private WPI_VictorSPX frontMotor;
    private WPI_VictorSPX backMotor;

    private int state = 0;

    private Notifier notifier;

    private Climb() {
        frontSolenoid = new SnailDoubleSolenoid(RobotMap.PCM_SECONDARY_ID, RobotMap.CLIMB_FRONT_SOLENOID_FORWARD, RobotMap.CLIMB_FRONT_SOLENOID_REVERSE);
        backSolenoid = new SnailDoubleSolenoid(RobotMap.PCM_SECONDARY_ID, RobotMap.CLIMB_BACK_SOLENOID_FORWARD, RobotMap.CLIMB_BACK_SOLENOID_REVERSE);

        frontMotor = new WPI_VictorSPX(RobotMap.CLIMB_FRONT_MOTOR);
        backMotor = new WPI_VictorSPX(RobotMap.CLIMB_BACK_MOTOR);
        frontMotor.setNeutralMode(NeutralMode.Brake);
        backMotor.setNeutralMode(NeutralMode.Brake);

        notifier = new Notifier(this::correctAngle);
        notifier.startPeriodic(RobotMap.CLIMB_UPDATE_PERIOD);

        reset();
    }
    
    // Retract both front and back
    public void reset() {
        retractFront();
        retractBack();

        state = 0;
    }

    public void toggleFront() {
        if(isFrontExtended()) retractFront();
        else extendFront();
    }

    public void retractFront() {
        frontSolenoid.set(Value.kForward);
    }

    public void extendFront() {
        frontSolenoid.set(Value.kReverse); 
    }

    public void turnOffFront() {
        frontSolenoid.set(Value.kOff);
    }

    public void toggleBack() {
        if(isBackExtended()) retractBack();
        else extendBack();
    }

    public void retractBack() {
        backSolenoid.set(Value.kForward);
    }

    public void extendBack() {
        backSolenoid.set(Value.kReverse);
    }

    public void turnOffBack() {
        backSolenoid.set(Value.kOff);
    }

    /* Go to the next state of the climb
     * 0 - Both front and back are retracted
     * 1 - Both front and back are extended
     * 2 - Back is retracted, front is extended
     */
    public void advanceClimb() {
        if(state == 0) {
            state = 1;
            extendFront();
            extendBack();
        }
        else if(state == 1) {
            state = 2;
            retractBack();
        }
        else if(state == 2) {
            state = 0;
            retractFront();
        }
    }

    public void climbDrive(double speed) {
        double adjustedSpeed = speed * RobotMap.CLIMB_MOTOR_MAX_SPEED;
        frontMotor.set(adjustedSpeed);
        backMotor.set(adjustedSpeed);
    }

    // If the robot is tilted beyond a critical angle while rising, stop appropriate solenoid
    private void correctAngle() {
        if(state == 1) {
            double angle = Gyro.getInstance().getClimbTiltAngle();
            // Robot is tilted forwards, so stop back
            if(angle > RobotMap.CLIMB_CRITICAL_ANGLE) {
                turnOffBack();
            }
            // Robot is tilted backwards, so stop front
            else if(angle < -RobotMap.CLIMB_CRITICAL_ANGLE) {
                turnOffFront();
            }
            // Otherwise, just extend both
            else {
                extendFront();
                extendBack();
            }
        }
    }

    // Whether or not the front is currently extended
    public boolean isFrontExtended() {
        return frontSolenoid.get() == DoubleSolenoid.Value.kReverse;
    }

    // Whether or not the back is currently extended
    public boolean isBackExtended() {
        return backSolenoid.get() == DoubleSolenoid.Value.kReverse;
    }

    public void outputValues() {
        SmartDashboard.putBoolean("Climb Front Extended", isFrontExtended());
        SmartDashboard.putBoolean("Climb Back Extended", isBackExtended());
        SmartDashboard.putNumber("Climb Front Speed", frontMotor.get());
        SmartDashboard.putNumber("Climb Back Speed", backMotor.get());
        SmartDashboard.putNumber("Climb State", state);
    }
    
    public static Climb getInstance() { 
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }
}