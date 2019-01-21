package frc.subsystems;

import frc.robot.RobotMap;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * <h1>Drivetrain</h1>
 * This is the drivetrain subsystem.
 * @author Allen Du, Arthur Chen, Om Desai, and Nicole Giron
 * @since 2019-01-21
 */
public class DriveTrain {
    private static DriveTrain instance = null;

    private FlakeMin flDrive;
    private FlakeMin frDrive;
    private CANSparkMax blDrive;
    private CANSparkMax brDrive;

    private DifferentialDrive driveTrain;

    /**
     * Constructs a new {@code DriveTrain} object.
     */
    public DriveTrain() {

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

    /**
     * Singleton.
     */
    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    public void configSpeedControllers() {
        flDrive.setSmartCurrentLimit(RobotMap.NEO_CONSTS[0], RobotMap.NEO_CONSTS[1], RobotMap.NEO_CONSTS[3]);
        frDrive.setSmartCurrentLimit(RobotMap.NEO_CONSTS[0], RobotMap.NEO_CONSTS[1], RobotMap.NEO_CONSTS[3]);
    }

    /**
     * This is just {@code arcadeDrive}.
     * @param x
     * @param z
     */
    public void drive(double x, double z) {
        driveTrain.arcadeDrive(x, z);
    }
}