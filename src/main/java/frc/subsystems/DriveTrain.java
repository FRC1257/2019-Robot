package frc.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

    private static DriveTrain instance = null;

    public CANSparkMax flDrive;
    public CANSparkMax frDrive;
    public CANSparkMax blDrive;
    public CANSparkMax brDrive;

    private DifferentialDrive driveTrain;

    double m_maxOutput;

    private DriveTrain() {
        flDrive = new CANSparkMax(RobotMap.DRIVE_FRONT_LEFT, MotorType.kBrushless);
        frDrive = new CANSparkMax(RobotMap.DRIVE_FRONT_RIGHT, MotorType.kBrushless);
        blDrive = new CANSparkMax(RobotMap.DRIVE_BACK_LEFT, MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.DRIVE_BACK_RIGHT, MotorType.kBrushless);

        flDrive.setIdleMode(IdleMode.kBrake);
        frDrive.setIdleMode(IdleMode.kBrake);
        blDrive.setIdleMode(IdleMode.kBrake);
        brDrive.setIdleMode(IdleMode.kBrake);

        flDrive.setOpenLoopRampRate(2.0);
        frDrive.setOpenLoopRampRate(2.0);
        blDrive.setOpenLoopRampRate(2.0);
        brDrive.setOpenLoopRampRate(2.0);

        blDrive.follow(flDrive);
        brDrive.follow(frDrive);

        driveTrain = new DifferentialDrive(flDrive, frDrive);
    }

    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    public void drive(double x, double z) {
        driveTrain.arcadeDrive(x, z);
    }
}