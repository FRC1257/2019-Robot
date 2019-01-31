package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Climb {
    
    private static Climb instance = null;

    private DoubleSolenoid frontSolenoid;
    private DoubleSolenoid backSolenoid;

    private WPI_VictorSPX climbMotorF;
    private WPI_VictorSPX climbMotorB;

    public Climb() {
        frontSolenoid = new DoubleSolenoid(RobotMap.CLIMB_SOLENOID_F_PORT_1, RobotMap.CLIMB_SOLENOID_F_PORT_2);
        backSolenoid = new DoubleSolenoid(RobotMap.CLIMB_SOLENOID_B_PORT_1, RobotMap.CLIMB_SOLENOID_B_PORT_2);

        climbMotorF = new WPI_VictorSPX(RobotMap.CLIMB_MOTOR_F);
        climbMotorB = new WPI_VictorSPX(RobotMap.CLIMB_MOTOR_B);
    }
    // gets value of climbState
    public int getState() {
        if(frontSolenoid.get() == DoubleSolenoid.Value.kReverse && backSolenoid.get() == DoubleSolenoid.Value.kReverse){
         return 1;
        }
        else if(frontSolenoid.get() == DoubleSolenoid.Value.kForward && backSolenoid.get() == DoubleSolenoid.Value.kForward){
         return 2;    
        }
        else if(frontSolenoid.get() == DoubleSolenoid.Value.kReverse && backSolenoid.get() == DoubleSolenoid.Value.kForward){
         return 3;    
        }
        else{
            return 0;
        }
    }
    public boolean getFront() {// gets value of frontOn
        if(frontSolenoid.get() == DoubleSolenoid.Value.kForward)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean getBack() { // gets value of backOn
        if(backSolenoid.get() == DoubleSolenoid.Value.kForward)
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
    }
    public void phase2Climb(){
        frontSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void phase3Climb(){
        backSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void reset() {// resets solenoids
        frontSolenoid.set(DoubleSolenoid.Value.kReverse);
        backSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void climbDrive(double s) {// drives the robot
        climbMotorF.set(s);
        climbMotorB.set(s);
    }
    public void smartDashboardClimb()//SmartDashboarding
    {
        SmartDashboard.putBoolean(RobotMap.FRONT_CLIMB_ON, getFront());
        SmartDashboard.putBoolean(RobotMap.BACK_CLIMB_ON, getBack());
        SmartDashboard.putNumber(RobotMap.CLIMB_MOTOR_VELOCITY, climbMotorF.get());
    }
    public static Climb getInstance() { 
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }
}