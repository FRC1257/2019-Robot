package frc.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * <h1>Drivetrain</h1>
 * This is the drivetrain subsystem.
 * @author Allen Du
 * @since 2019-01-21
 */
public class DriveTrain {
    private static DriveTrain instance = null;

    private FlakeMin flDrive;
    private FlakeMin frDrive;
    private CANSparkMax blDrive;
    private CANSparkMax brDrive;

    private DifferentialDrive driveTrain;

    public boolean reverse = false;

    /**
     * Constructs a new {@code DriveTrain} object.
     */
    public DriveTrain() {

        flDrive = new FlakeMin(RobotMap.DRIVE_MOTORS[0], MotorType.kBrushless, true);
        frDrive = new FlakeMin(RobotMap.DRIVE_MOTORS[1], MotorType.kBrushless, false);
        blDrive = new CANSparkMax(RobotMap.DRIVE_MOTORS[2], MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.DRIVE_MOTORS[3], MotorType.kBrushless);

        configSpeedControllers();

        blDrive.follow(flDrive);
        brDrive.follow(frDrive);

        driveTrain = new DifferentialDrive(flDrive, frDrive);
    }

    /**
     * Singleton.
     * @return A Drivetrain object.
     */
    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    /**
     * Sets voltage and RPM limits on motor controllers.
     */
    public void configSpeedControllers() {
        flDrive.setSmartCurrentLimit(RobotMap.DRIVE_NEO_CONSTS[0], RobotMap.DRIVE_NEO_CONSTS[1], RobotMap.DRIVE_NEO_CONSTS[3]);
        frDrive.setSmartCurrentLimit(RobotMap.DRIVE_NEO_CONSTS[0], RobotMap.DRIVE_NEO_CONSTS[1], RobotMap.DRIVE_NEO_CONSTS[3]);
    }

    /**
     * This is {@code arcadeDrive}.
     * Modified to accomodate a reverse drive mode.
     * @param x Forward speed, from -1 to 1.
     * @param z Rate of rotation, from -1 to 1.
     */
    public void drive(double x, double z) {
        driveTrain.arcadeDrive(x, z);
        if(reverse) {
            driveTrain.arcadeDrive(-x, z);
        }
    }
}