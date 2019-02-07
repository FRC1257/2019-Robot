package frc.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    private boolean reverse;

    /**
     * Constructs a new {@code DriveTrain} object.
     */
    public DriveTrain() {

        flDrive = new FlakeMin(RobotMap.DRIVE_FL_MOTOR_ID, MotorType.kBrushless, true);
        frDrive = new FlakeMin(RobotMap.DRIVE_FR_MOTOR_ID, MotorType.kBrushless, false);
        blDrive = new CANSparkMax(RobotMap.DRIVE_BL_MOTOR_ID, MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.DRIVE_BR_MOTOR_ID, MotorType.kBrushless);

        configSpeedControllers();

        blDrive.follow(flDrive);
        brDrive.follow(frDrive);

        driveTrain = new DifferentialDrive(flDrive, frDrive);

        reverse = false;
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
        x = applyDeadband(x);
        z = applyDeadband(z);
        if(!reverse) driveTrain.arcadeDrive(x, z);
        else driveTrain.arcadeDrive(-x, z);
    }

    public void toggleReverse() {
        reverse = !reverse;
    }

    public boolean isReversed() {
        return reverse;
    }

    public FlakeMin getFRDrive() {
        return frDrive;
    }

    public FlakeMin getFLDrive() {
        return flDrive;
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

    public void outputValues() {
        flDrive.outputValues();
        frDrive.outputValues();

        SmartDashboard.putBoolean("Drive Reversed", reverse);
    }

    public void setConstantTuning() {
        SmartDashboard.putNumber("Drive Left P", RobotMap.DRIVE_PIDF_LEFT[0]);
        SmartDashboard.putNumber("Drive Left I", RobotMap.DRIVE_PIDF_LEFT[1]);
        SmartDashboard.putNumber("Drive Left D", RobotMap.DRIVE_PIDF_LEFT[2]);
        SmartDashboard.putNumber("Drive Left F", RobotMap.DRIVE_PIDF_LEFT[3]);

        SmartDashboard.putNumber("Drive Right P", RobotMap.DRIVE_PIDF_RIGHT[0]);
        SmartDashboard.putNumber("Drive Right I", RobotMap.DRIVE_PIDF_RIGHT[1]);
        SmartDashboard.putNumber("Drive Right D", RobotMap.DRIVE_PIDF_RIGHT[2]);
        SmartDashboard.putNumber("Drive Right F", RobotMap.DRIVE_PIDF_RIGHT[3]);

        SmartDashboard.putNumber("Drive Washout", RobotMap.DRIVE_P_WASHOUT);
    }

    public void updateConstantTuning() {
        RobotMap.DRIVE_PIDF_LEFT[0] = SmartDashboard.getNumber("Drive Left P", RobotMap.DRIVE_PIDF_LEFT[0]);
        RobotMap.DRIVE_PIDF_LEFT[1] = SmartDashboard.getNumber("Drive Left I", RobotMap.DRIVE_PIDF_LEFT[1]);
        RobotMap.DRIVE_PIDF_LEFT[2] = SmartDashboard.getNumber("Drive Left D", RobotMap.DRIVE_PIDF_LEFT[2]);
        RobotMap.DRIVE_PIDF_LEFT[3] = SmartDashboard.getNumber("Drive Left F", RobotMap.DRIVE_PIDF_LEFT[3]);
        flDrive.updatePID(true);

        RobotMap.DRIVE_PIDF_RIGHT[0] = SmartDashboard.getNumber("Drive Right P", RobotMap.DRIVE_PIDF_RIGHT[0]);
        RobotMap.DRIVE_PIDF_RIGHT[1] = SmartDashboard.getNumber("Drive Right I", RobotMap.DRIVE_PIDF_RIGHT[1]);
        RobotMap.DRIVE_PIDF_RIGHT[2] = SmartDashboard.getNumber("Drive Right D", RobotMap.DRIVE_PIDF_RIGHT[2]);
        RobotMap.DRIVE_PIDF_RIGHT[3] = SmartDashboard.getNumber("Drive Right F", RobotMap.DRIVE_PIDF_RIGHT[3]);
        frDrive.updatePID(false);

        RobotMap.DRIVE_P_WASHOUT = SmartDashboard.getNumber("Drive Washout", RobotMap.DRIVE_P_WASHOUT);
    }

    private double applyDeadband(double in) {
        if(Math.abs(in) < RobotMap.NEO_DEADBAND) {
            return 0;
        }
        return in;
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