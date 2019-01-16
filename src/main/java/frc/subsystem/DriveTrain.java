package frc.subsystem;

import frc.robot.RobotMap;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
    private static DriveTrain instance = null;

    private FlakeMin flDrive;
    private FlakeMin frDrive;
    private CANSparkMax blDrive;
    private CANSparkMax brDrive;

    double m_maxOutput;

    private DriveTrain() {
        DifferentialDrive driveTrain;

        flDrive = new FlakeMin(RobotMap.MOTORS[0], MotorType.kBrushless, true);
        frDrive = new FlakeMin(RobotMap.MOTORS[1], MotorType.kBrushless, false);
        blDrive = new CANSparkMax(RobotMap.MOTORS[2], MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.MOTORS[3], MotorType.kBrushless);

        flDrive.getPID();
        frDrive.getPID();

        configSpeedControllers();

        blDrive.follow(flDrive);
        brDrive.follow(frDrive);

        driveTrain = new DifferentialDrive(flDrive, frDrive);
    }

    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    private void configSpeedControllers() {
        flDrive.setSmartCurrentLimit(RobotMap.NEO_CONSTS[0], RobotMap.NEO_CONSTS[1], RobotMap.NEO_CONSTS[3]);
        frDrive.setSmartCurrentLimit(RobotMap.NEO_CONSTS[0], RobotMap.NEO_CONSTS[1], RobotMap.NEO_CONSTS[3]);
    }

    private void drive(double x, double z) {
        arcadeDrive(x, z);
    }
}