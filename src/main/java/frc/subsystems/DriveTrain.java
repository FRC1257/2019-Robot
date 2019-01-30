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

    public FlakeMin flDrive;
    public FlakeMin frDrive;
    public CANSparkMax blDrive;
    public CANSparkMax brDrive;

    private DifferentialDrive driveTrain;

    public boolean reverse = false;

    /**
     * Constructs a new {@code DriveTrain} object.
     */
    public DriveTrain() {

        flDrive = new FlakeMin(RobotMap.DRIVE_FL, MotorType.kBrushless, true);
        frDrive = new FlakeMin(RobotMap.DRIVE_FR, MotorType.kBrushless, false);
        blDrive = new CANSparkMax(RobotMap.DRIVE_BL, MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.DRIVE_BR, MotorType.kBrushless);

        configSpeedControllers();

        blDrive.follow(flDrive);
        brDrive.follow(frDrive);

        driveTrain = new DifferentialDrive(flDrive, frDrive);
    }

    /**
     * Sets voltage and RPM limits on motor controllers.
     */
    public void configSpeedControllers() {
        flDrive.setSmartCurrentLimit(RobotMap.NEO_STALL_LIMIT, RobotMap.NEO_RUN_CURRENT, RobotMap.NEO_MIN_RPM);
        frDrive.setSmartCurrentLimit(RobotMap.NEO_STALL_LIMIT, RobotMap.NEO_RUN_CURRENT, RobotMap.NEO_MIN_RPM);
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

    public void getLeftEncoderPosition() {
        flDrive.getEncoderPosition();
    }

    public void getRightEncoderPosition() {
        frDrive.getEncoderPosition();
    }

    public void getRightEncoderVelocity() {
        frDrive.getEncoderVelocity();
    }

    public void getLeftEncoderVelocity() {
        flDrive.getEncoderVelocity();
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
}