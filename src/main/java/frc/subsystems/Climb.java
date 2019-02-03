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
    }
    
    // Raise both front and back
    public void reset() {
        lowerFront();
        lowerBack();
    }

    public void toggleFront() {
        if(getFront()) raiseFront();
        else lowerFront();
    }

    public void raiseFront() {
        frontSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void lowerFront() {
        frontSolenoid.set(DoubleSolenoid.Value.kReverse); 
    }

    public void toggleBack() {
        if(getBack()) raiseBack();
        else lowerBack();
    }

    public void raiseBack() {
        backSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void lowerBack() {
        backSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    /* Go to the next state of the climb
     * 0 - Both front and back are not raised
     * 1 - Both front and back are raised
     * 2 - Front is not raised, back is raised
     * 3 - Front is raised, back is not raised
     */
    public void advanceClimb(boolean reverseDrive) {
        int state = getState();
        if(state == 0) {
            raiseFront();
            raiseBack();
        }
        else if(state == 1) {
            if(reverseDrive) {
                lowerFront();
            }
            else {
                lowerBack();
            }
        }
        else if(state == 2) {
            lowerBack();
        }
        else if(state == 3) {
            lowerFront();
        }
    }

    /* Returns the current state of the climb
     * 0 - Both front and back are not raised
     * 1 - Both front and back are raised
     * 2 - Front is not raised, back is raised
     * 3 - Front is raised, back is not raised
     */ 
    public int getState() {
        if(!getFront() && !getBack()) {
            return 0;
        }
        else if(getFront() && getBack()) {
            return 1;
        }
        else if(!getFront() && getBack()) {
            return 2;
        }
        else if(getFront() && !getBack()) {
            return 3;
        }

        return -1; // Should not ever be returned
    }

    public void climbDrive(double speed) {
        if(getFront()) frontMotor.set(speed);
        if(getBack()) backMotor.set(speed);
    }

    // Whether or not the front is currently raised
    public boolean getFront() {
        return frontSolenoid.get() == DoubleSolenoid.Value.kReverse;
    }

    // Whether or not the front is currently raised
    public boolean getBack() {
        return backSolenoid.get() == DoubleSolenoid.Value.kForward;
    }

    public void outputValues() {
        SmartDashboard.putBoolean("Climb Front Raised", getFront());
        SmartDashboard.putBoolean("Climb Back Raised", getBack());
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