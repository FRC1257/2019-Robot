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

    public Climb() {
        front = new DoubleSolenoid(RobotMap.ClimbSolenoidFPort1, RobotMap.ClimbSolenoidFPort2);
        back = new DoubleSolenoid(RobotMap.ClimbSolenoidBPort1, RobotMap.ClimbSolenoidBPort2);

        motorF = new WPI_VictorSPX(RobotMap.ClimbMotorsF);
        motorB = new WPI_VictorSPX(RobotMap.ClimbMotorsB);

        frontOn = false;
        backOn = false;

        
    }

    public static Climb getInstance() { 
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }
    public boolean getFront()
    {
        return frontOn;
    }
    public boolean getBack()
    {
        return backOn;    
    }
    public void frontForward() {  //pushes forwards front solenoid
        front.set(DoubleSolenoid.Value.kForward);
        this.frontOn=true;
    }

    public void frontReverse() { // reverses front solenoid
        front.set(DoubleSolenoid.Value.kReverse);
        this.frontOn=false; 
    }

    public void backForward() {  //pushes forward back solenoid
        back.set(DoubleSolenoid.Value.kForward);
        this.backOn = true;
    }

    public void backReverse() { // reverses back solenoid
        back.set(DoubleSolenoid.Value.kReverse);
        this.backOn = false;
    }

    public void reset() { // resets solenoids
        front.set(DoubleSolenoid.Value.kReverse);
        back.set(DoubleSolenoid.Value.kReverse);
        frontOn = false;
        backOn = false;
    }

    public void climbDrive(double f) { // drives the robot front/back
        motorF.set(f);
        motorB.set(f);
    }
}