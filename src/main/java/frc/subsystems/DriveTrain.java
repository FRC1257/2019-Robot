package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.*;

public class DriveTrain {
    private static DriveTrain instance = null;

    private WPI_TalonSRX flDrive;
    private WPI_TalonSRX frDrive;
    private WPI_TalonSRX blDrive;
    private WPI_TalonSRX brDrive;

    private DifferentialDrive driveTrain;

    double m_maxOutput;

    private DriveTrain() {

        flDrive = new WPI_TalonSRX(RobotMap.MOTORS[0]);
        frDrive = new WPI_TalonSRX(RobotMap.MOTORS[1]);
        blDrive = new WPI_TalonSRX(RobotMap.MOTORS[2]);
        brDrive = new WPI_TalonSRX(RobotMap.MOTORS[3]);

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