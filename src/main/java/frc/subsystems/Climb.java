package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Climb {
    
    private static Climb instance = null;

    private DoubleSolenoid frontSolenoid;
    private DoubleSolenoid backSolenoid;

    private WPI_VictorSPX climbMotorF;
    private WPI_VictorSPX climbMotorB;

    private int climbState;

    public Climb() {
        frontSolenoid = new DoubleSolenoid(RobotMap.CLIMB_SOLENOID_F_PORT_1, RobotMap.CLIMB_SOLENOID_F_PORT_2);
        backSolenoid = new DoubleSolenoid(RobotMap.CLIMB_SOLENOID_B_PORT_1, RobotMap.CLIMB_SOLENOID_B_PORT_2);

        climbMotorF = new WPI_VictorSPX(RobotMap.CLIMB_MOTOR_F);
        climbMotorB = new WPI_VictorSPX(RobotMap.CLIMB_MOTOR_B);

        climbState = 1;
    }

    // gets value of climbState
    public int getState() {
        return climbState;
    }

    // gets value of frontOn
    public boolean getFront() {
        if(frontSolenoid.get() == Value.kForward)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // gets value of backOn
    public boolean getBack() {
        if(backSolenoid.get() == Value.kForward)
        {
            return true;
        }
        else
        {
            return false;
        }  
    }
    public void frontForward() {// pushes forwards frontSolenoid solenoid
        frontSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void frontReverse() {// reverses frontSolenoid solenoid
        frontSolenoid.set(DoubleSolenoid.Value.kReverse); 
    }
    public void backForward() {// pushes forward backSolenoid solenoid
        backSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void backReverse() { // reverses backSolenoid solenoid
        backSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    // phases of the climb
    public void phase1Climb(){
        frontSolenoid.set(DoubleSolenoid.Value.kForward);
        backSolenoid.set(DoubleSolenoid.Value.kForward);
        this.climbState = 2;
    }
    public void phase2Climb(){
        frontSolenoid.set(DoubleSolenoid.Value.kReverse);
        this.climbState = 3;
    }
    public void phase3Climb(){
        backSolenoid.set(DoubleSolenoid.Value.kReverse);
        this.climbState = 1;
    }
    // resets solenoids
    public void reset() {
        frontSolenoid.set(DoubleSolenoid.Value.kReverse);
        backSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    // drives the robot frontSolenoid/backSolenoid
    public void climbDrive(double f) {
        climbMotorF.set(f);
        climbMotorB.set(f);
    }
    public static Climb getInstance() { 
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }
}