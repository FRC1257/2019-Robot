package frc.subsystems;

import frc.robot.RobotMap;
import frc.util.SnailDoubleSolenoid;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Climb {
    
    private static Climb instance = null;

    private SnailDoubleSolenoid frontSolenoid;
    private SnailDoubleSolenoid backSolenoid;

    private WPI_VictorSPX frontMotor;
    private WPI_VictorSPX backMotor;

    private Climb() {
        frontSolenoid = new SnailDoubleSolenoid(RobotMap.CLIMB_FRONT_SOLENOID_FORWARD, RobotMap.CLIMB_FRONT_SOLENOID_REVERSE);
        backSolenoid = new SnailDoubleSolenoid(RobotMap.CLIMB_BACK_SOLENOID_FORWARD, RobotMap.CLIMB_BACK_SOLENOID_REVERSE);

        frontMotor = new WPI_VictorSPX(RobotMap.CLIMB_FRONT_MOTOR);
        backMotor = new WPI_VictorSPX(RobotMap.CLIMB_BACK_MOTOR);

        reset();
    }
    
    // Raise both front and back
    public void reset() {
        retractFront();
        retractBack();
    }

    public void toggleFront() {
        if(isFrontExtended()) retractFront();
        else extendFront();
    }

    public void retractFront() {
        frontSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void extendFront() {
        frontSolenoid.set(DoubleSolenoid.Value.kForward); 
    }

    public void toggleBack() {
        if(isBackExtended()) retractBack();
        else extendBack();
    }

    public void retractBack() {
        backSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void extendBack() {
        backSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    /* Go to the next state of the climb
     * 0 - Both front and back are retracted
     * 1 - Both front and back are extended
     * 2 - Front is retracted, back is extended
     * 3 - Front is extended, back is retracted
     */
    public void advanceClimb() {
        int state = getState();
        if(state == 0) {
            extendFront();
            extendBack();
        }
        else if(state == 1) {
            retractFront();
        }
        else if(state == 2) {
            retractBack();
        }
        else if(state == 3) { // Shouldn't happen
            retractFront();
        }
    }

    /* Returns the current state of the climb
     * 0 - Both front and back are retracted
     * 1 - Both front and back are extended
     * 2 - Front is retracted, back is extended
     * 3 - Front is extended, back is retracted
     */
    public int getState() {
        if(!isFrontExtended() && !isBackExtended()) {
            return 0;
        }
        else if(isFrontExtended() && isBackExtended()) {
            return 1;
        }
        else if(!isFrontExtended() && isBackExtended()) {
            return 2;
        }
        else if(isFrontExtended() && !isBackExtended()) { // Shouldn't happen
            return 3;
        }

        return -1; // Should not ever be returned
    }

    public void climbDrive(double speed) {
        if(isFrontExtended()) frontMotor.set(speed);
        if(isBackExtended()) backMotor.set(speed);
    }

    // Whether or not the front is currently raised
    public boolean isFrontExtended() {
        return frontSolenoid.get() == DoubleSolenoid.Value.kForward;
    }

    // Whether or not the front is currently raised
    public boolean isBackExtended() {
        return backSolenoid.get() == DoubleSolenoid.Value.kForward;
    }

    public void outputValues() {
        SmartDashboard.putBoolean("Climb Front Extended", isFrontExtended());
        SmartDashboard.putBoolean("Climb Back Extended", isBackExtended());
        SmartDashboard.putNumber("Climb Front Speed", frontMotor.get());
        SmartDashboard.putNumber("Climb Back Speed", backMotor.get());
    }
    
    public static Climb getInstance() { 
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }
}