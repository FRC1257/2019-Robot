package frc.drivetrain;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import java.util.StringJoiner;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveTrain {
    private static DriveTrain instance = null;

    private CANSparkMax flDrive;
    private CANSparkMax frDrive;
    private CANSparkMax blDrive;
    private CANSparkMax brDrive;

    private CANPIDController flPID;
    private CANPIDController frPID;
    private CANPIDController blPID;
    private CANPIDController brPID;

    private SpeedControllerGroup lDrive;
    private SpeedControllerGroup rDrive;

    private IntegralDrive driveTrain;

    private double xSpeed;
    private double zRotation;

    double m_maxOutput;

    private DriveTrain() {
        flDrive = new CANSparkMax(RobotMap.motors[0], MotorType.kBrushless);
        frDrive = new CANSparkMax(RobotMap.motors[1], MotorType.kBrushless);
        blDrive = new CANSparkMax(RobotMap.motors[2], MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.motors[3], MotorType.kBrushless);

        lDrive = new SpeedControllerGroup(flDrive, blDrive);
        rDrive = new SpeedControllerGroup(frDrive, brDrive);

        driveTrain = new IntegralDrive(lDrive, rDrive);

        configSpeedControllers();
        initMotorPID();
        configMotorPID();
    }

    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    private void configSpeedControllers() {
        flDrive.setSmartCurrentLimit(RobotMap.NEOconsts[0], RobotMap.NEOconsts[1], RobotMap.NEOconsts[3]);
        frDrive.setSmartCurrentLimit(RobotMap.NEOconsts[0], RobotMap.NEOconsts[1], RobotMap.NEOconsts[3]);
        blDrive.setSmartCurrentLimit(RobotMap.NEOconsts[0], RobotMap.NEOconsts[1], RobotMap.NEOconsts[3]);
        brDrive.setSmartCurrentLimit(RobotMap.NEOconsts[0], RobotMap.NEOconsts[1], RobotMap.NEOconsts[3]);
    }

    private void initMotorPID() {
        flPID = new CANPIDController(flDrive);
        frPID = new CANPIDController(frDrive);
        blPID = new CANPIDController(blDrive);
        brPID = new CANPIDController(brDrive);
    }

    private void configMotorPID() {
        flPID.setFF(RobotMap.clPIDl[0]);
        flPID.setP(RobotMap.clPIDl[1]);
        flPID.setI(RobotMap.clPIDl[2]);
        flPID.setD(RobotMap.clPIDl[3]);

        frPID.setFF(RobotMap.clPIDl[0]);
        frPID.setP(RobotMap.clPIDl[1]);
        frPID.setI(RobotMap.clPIDl[2]);
        frPID.setD(RobotMap.clPIDl[3]);

        blPID.setFF(RobotMap.clPIDr[0]);
        blPID.setP(RobotMap.clPIDr[1]);
        blPID.setI(RobotMap.clPIDr[2]);
        blPID.setD(RobotMap.clPIDr[3]);

        brPID.setFF(RobotMap.clPIDr[0]);
        brPID.setP(RobotMap.clPIDr[1]);
        brPID.setI(RobotMap.clPIDr[2]);
        brPID.setD(RobotMap.clPIDr[3]);
    }

    public void setSetPIDlr(double lIn, double rIn) {
        flPID.setReference(RobotMap.NEOconsts[2] * lIn, ControlType.kVelocity);
        frPID.setReference(RobotMap.NEOconsts[2] * rIn, ControlType.kVelocity);
        blPID.setReference(RobotMap.NEOconsts[2] * lIn, ControlType.kVelocity);
        brPID.setReference(RobotMap.NEOconsts[2] * rIn, ControlType.kVelocity);
    }
}