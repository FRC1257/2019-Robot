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

    private Climb() {
        frontLeft = new DoubleSolenoid(1, 2);
        frontRight = new DoubleSolenoid(3, 4);
        backLeft = new DoubleSolenoid(5, 6);
        backRight = new DoubleSolenoid(7, 8);
    

    }

    public static Climb getInstance() {
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }

    public void frontForward()
    {
        frontLeft.set(DoubleSolenoid.Value.kForward);
        frontRight.set(DoubleSolenoid.Value.kForward);
    }

    public void frontReverse()
    {
        frontLeft.set(DoubleSolenoid.Value.kReverse);
        frontRight.set(DoubleSolenoid.Value.kReverse);  
    }

    public void backFoward()
    {
        backLeft.set(DoubleSolenoid.Value.kForward);
        backRight.set(DoubleSolenoid.Value.kForward);
    }

    public void backReverse()
    {
        backLeft.set(DoubleSolenoid.Value.kReverse);
        backRight.set(DoubleSolenoid.Value.kReverse);
    }

    public void reset()
    {
        frontLeft.set(DoubleSolenoid.Value.kReverse);
        frontRight.set(DoubleSolenoid.Value.kReverse);
        backLeft.set(DoubleSolenoid.Value.kReverse);
        backRight.set(DoubleSolenoid.Value.kReverse);
    }