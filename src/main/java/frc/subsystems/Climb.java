package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Climb {
    
    private static Climb instance = null;

    DoubleSolenoid front;
    DoubleSolenoid back;

    WPI_VictorSPX motorF;
    WPI_VictorSPX motorB;

    private boolean frontOn;
    private boolean backOn;
    private int climbState;

    public Climb() {
        front = new DoubleSolenoid(RobotMap.ClimbSolenoidFPort1, RobotMap.ClimbSolenoidFPort2);
        back = new DoubleSolenoid(RobotMap.ClimbSolenoidBPort1, RobotMap.ClimbSolenoidBPort2);

        motorF = new WPI_VictorSPX(RobotMap.ClimbMotorsF);
        motorB = new WPI_VictorSPX(RobotMap.ClimbMotorsB);

        frontOn = false;
        backOn = false;
        climbState = 1;
        
        
    }

    // gets value of climbState
    public int getState() {
        return climbState;
    }

    // gets value of frontOn
    public boolean getFront() {
        return frontOn;
    }

    // gets value of backOn
    public boolean getBack() {
        return backOn;    
    }

    // pushes forwards front solenoid
    public void frontForward() {
        front.set(DoubleSolenoid.Value.kForward);
        this.frontOn = true;
    }

    // reverses front solenoid
    public void frontReverse() {
        front.set(DoubleSolenoid.Value.kReverse);
        this.frontOn = false; 
    }

    // pushes forward back solenoid
    public void backForward() {
        back.set(DoubleSolenoid.Value.kForward);
        this.backOn = true;
    }

    // reverses back solenoid
    public void backReverse() {
        back.set(DoubleSolenoid.Value.kReverse);
        this.backOn = false;
    }

    // phases of the climb
    public void phase1Climb(){
        front.set(DoubleSolenoid.Value.kForward);
        back.set(DoubleSolenoid.Value.kForward);
        this.backOn = true;
        this.frontOn = true;
        this.climbState = 2;
    }

    public void phase2Climb(){
        front.set(DoubleSolenoid.Value.kReverse);
        this.frontOn = false;
        this.climbState = 3;
    }

    public void phase3Climb(){
        back.set(DoubleSolenoid.Value.kReverse);
        this.backOn = false;
        this.climbState = 1;
    }

    // resets solenoids
    public void reset() {
        front.set(DoubleSolenoid.Value.kReverse);
        back.set(DoubleSolenoid.Value.kReverse);
        frontOn = false;
        backOn = false;
    }

    // drives the robot front/back
    public void climbDrive(double f) {
        motorF.set(f);
        motorB.set(f);
    }

    public static Climb getInstance() { 
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }
}