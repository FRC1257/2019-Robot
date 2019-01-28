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

    public boolean FrontOn;
    public boolean BackOn;

    public Climb() {
        front = new DoubleSolenoid(RobotMap.ClimbSolenoidFPort1, RobotMap.ClimbSolenoidFPort2);
        back = new DoubleSolenoid(RobotMap.ClimbSolenoidBPort1, RobotMap.ClimbSolenoidBPort2);

        motorF = new WPI_VictorSPX(RobotMap.ClimbMotorsF);
        motorB = new WPI_VictorSPX(RobotMap.ClimbMotorsB);

        FrontOn = false;
        BackOn = false;

        
    }

    public static Climb getInstance() { 
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }

    public void frontForward() {  //pushes forwards front solenoid
        front.set(DoubleSolenoid.Value.kForward);
        this.FrontOn=true;
    }

    public void frontReverse() { // reverses front solenoid
        front.set(DoubleSolenoid.Value.kReverse);
        this.FrontOn=false; 
    }

    public void backForward() {  //pushes forward back solenoid
        back.set(DoubleSolenoid.Value.kForward);
        this.BackOn = true;
    }

    public void backReverse() { // reverses back solenoid
        back.set(DoubleSolenoid.Value.kReverse);
        this.BackOn = false;
    }

    public void reset() { // resets solenoids
        front.set(DoubleSolenoid.Value.kReverse);
        back.set(DoubleSolenoid.Value.kReverse);
        FrontOn = false;
        BackOn = false;
    }

    public void climbDrive(double f) { // drives the robot front/back
        motorF.set(f);
        motorB.set(f);
    }
    

}