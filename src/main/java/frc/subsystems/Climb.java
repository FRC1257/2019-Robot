package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climb {
    
    private static Climb instance = null;
    DoubleSolenoid frontLeft;
    DoubleSolenoid frontRight;
    DoubleSolenoid backLeft;
    DoubleSolenoid backRight;
    public boolean FrontOn;
    public boolean BackOn;

    public Climb() {
        frontLeft = new DoubleSolenoid(1, 2);
        frontRight = new DoubleSolenoid(3, 4);
        backLeft = new DoubleSolenoid(5, 6);
        backRight = new DoubleSolenoid(7, 8);
        FrontOn = false;
        BackOn = false;
    }

    public static Climb getInstance() {
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }

    public void frontForward() {
        frontLeft.set(DoubleSolenoid.Value.kForward);
        frontRight.set(DoubleSolenoid.Value.kForward);
        this.FrontOn=true;
    }

    public void frontReverse() {
        frontLeft.set(DoubleSolenoid.Value.kReverse);
        frontRight.set(DoubleSolenoid.Value.kReverse);
        this.FrontOn=false; 
    }

    public void backForward() {
        backLeft.set(DoubleSolenoid.Value.kForward);
        backRight.set(DoubleSolenoid.Value.kForward);
        this.BackOn = true;
    }

    public void backReverse() {
        backLeft.set(DoubleSolenoid.Value.kReverse);
        backRight.set(DoubleSolenoid.Value.kReverse);
        this.BackOn = false;
    }

    public void reset() {
        frontLeft.set(DoubleSolenoid.Value.kReverse);
        frontRight.set(DoubleSolenoid.Value.kReverse);
        backLeft.set(DoubleSolenoid.Value.kReverse);
        backRight.set(DoubleSolenoid.Value.kReverse);
        FrontOn = false;
        BackOn = false;
    }
}