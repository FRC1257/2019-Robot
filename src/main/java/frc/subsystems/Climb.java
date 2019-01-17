package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climb {
    
    private static Climb instance = null;

    DoubleSolenoid front;
    DoubleSolenoid back;

    VictorSPX motorF;
    VictorSPX motorB;

    public boolean FrontOn;
    public boolean BackOn;

    public Climb() {
        front = new DoubleSolenoid(1, 2);
        back = new DoubleSolenoid(3, 4);
        motorF = new VictorSPX(5);
        motorB = new VictorSPX(6);
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
        front.set(DoubleSolenoid.Value.kForward);
        this.FrontOn=true;
    }

    public void frontReverse() {
        front.set(DoubleSolenoid.Value.kReverse);
        this.FrontOn=false; 
    }

    public void backForward() {
        back.set(DoubleSolenoid.Value.kForward);
        this.BackOn = true;
    }

    public void backReverse() {
        back.set(DoubleSolenoid.Value.kReverse);
        this.BackOn = false;
    }

    public void reset() {
        front.set(DoubleSolenoid.Value.kReverse);
        back.set(DoubleSolenoid.Value.kReverse);
        FrontOn = false;
        BackOn = false;
    }
    

}