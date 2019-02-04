package frc.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.*;
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

        flDrive = new CANSparkMax(RobotMap.MOTORS[0], MotorType.kBrushless);
        frDrive = new CANSparkMax(RobotMap.MOTORS[1], MotorType.kBrushless);
        blDrive = new CANSparkMax(RobotMap.MOTORS[2], MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.MOTORS[3], MotorType.kBrushless);

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

    public void drive(double x, double z) {
        driveTrain.arcadeDrive(x, z, false);
    }
}