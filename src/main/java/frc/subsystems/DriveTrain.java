package frc.subsystems;

import frc.robot.RobotMap;
import frc.util.Gyro;
import frc.util.SynchronousPIDF;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

    private static DriveTrain instance = null;

    public CANSparkMax flDrive;
    public CANSparkMax frDrive;
    public CANSparkMax blDrive;
    public CANSparkMax brDrive;

    private DifferentialDrive driveTrain;

    private boolean reversed;

    private Gyro gyro;
    private boolean turnPIDActive;
    private double lastTime;
    private SynchronousPIDF pidController;

    
    
    private DriveTrain() {
        flDrive = new CANSparkMax(RobotMap.DRIVE_FRONT_LEFT, MotorType.kBrushless);
        frDrive = new CANSparkMax(RobotMap.DRIVE_FRONT_RIGHT, MotorType.kBrushless);
        blDrive = new CANSparkMax(RobotMap.DRIVE_BACK_LEFT, MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.DRIVE_BACK_RIGHT, MotorType.kBrushless);

        flDrive.setIdleMode(IdleMode.kBrake);
        frDrive.setIdleMode(IdleMode.kBrake);
        blDrive.setIdleMode(IdleMode.kCoast);
        brDrive.setIdleMode(IdleMode.kCoast);

        flDrive.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);
        frDrive.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);
        blDrive.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);
        brDrive.setSmartCurrentLimit(RobotMap.NEO_CURRENT_LIMIT);

        flDrive.setOpenLoopRampRate(0.0);
        frDrive.setOpenLoopRampRate(0.0);
        blDrive.setOpenLoopRampRate(0.0);
        brDrive.setOpenLoopRampRate(0.0);

        blDrive.follow(flDrive);
        brDrive.follow(frDrive);

        driveTrain = new DifferentialDrive(flDrive, frDrive);

        gyro = Gyro.getInstance();
        pidController = new SynchronousPIDF(RobotMap.DRIVE_TURN_PIDF[0], RobotMap.DRIVE_TURN_PIDF[1], 
            RobotMap.DRIVE_TURN_PIDF[2], RobotMap.DRIVE_TURN_PIDF[3]);

        setConstantTuning();
        reset();
    }

    public void reset() {
        flDrive.set(0);
        frDrive.set(0);
        blDrive.set(0);
        brDrive.set(0);

        reversed = false;

        turnPIDActive = false;
        lastTime = -1;
    }

    public void drive(double x, double z) {
        if(reversed) {
            driveTrain.arcadeDrive(-x * RobotMap.MAX_DRIVE_SPEED, z * RobotMap.MAX_TURN_SPEED);
        }
        else {
            driveTrain.arcadeDrive(x * RobotMap.MAX_DRIVE_SPEED, z * RobotMap.MAX_TURN_SPEED);
        }
    }

    public void turnLeft() {
        if(!turnPIDActive) {
            gyro.zeroRobotAngle();
            pidController.reset();

            pidController.setSetpoint(-90);
            lastTime = Timer.getFPGATimestamp();

            turnPIDActive = true;
        }

        drive(0, pidController.calculate(gyro.getRobotAngle(), Timer.getFPGATimestamp() - lastTime));
        lastTime = Timer.getFPGATimestamp();
    }

    public void turnRight() {
        if(!turnPIDActive) {
            gyro.zeroRobotAngle();
            pidController.reset();

            pidController.setSetpoint(90);
            lastTime = Timer.getFPGATimestamp();

            turnPIDActive = true;
        }

        drive(0, pidController.calculate(gyro.getRobotAngle(), Timer.getFPGATimestamp() - lastTime));
        lastTime = Timer.getFPGATimestamp();
    }

    public void resetPID() {
        turnPIDActive = false;
        pidController.reset();
    }

    public void toggleReverse() {
        reversed = !reversed;
    }

    public boolean isReversed() {
        return reversed;
    }

    // Output values to Smart Dashboard
    public void outputValues() {
        SmartDashboard.putBoolean("Drive Reversed", reversed);

        SmartDashboard.putNumber("Drive FL Current", flDrive.getOutputCurrent());
        SmartDashboard.putNumber("Drive FR Current", frDrive.getOutputCurrent());
        SmartDashboard.putNumber("Drive BL Current", blDrive.getOutputCurrent());
        SmartDashboard.putNumber("Drive BR Current", brDrive.getOutputCurrent());
    }

    // Initialize constants in Smart Dashboard
    public void setConstantTuning() {
        SmartDashboard.putNumber("Drive Turn P", RobotMap.DRIVE_TURN_PIDF[0]);
        SmartDashboard.putNumber("Drive Turn I", RobotMap.DRIVE_TURN_PIDF[1]);
        SmartDashboard.putNumber("Drive Turn D", RobotMap.DRIVE_TURN_PIDF[2]);
        SmartDashboard.putNumber("Drive Turn F", RobotMap.DRIVE_TURN_PIDF[3]);
    }

    // Update constants from Smart Dashboard
    public void getConstantTuning() {
        RobotMap.DRIVE_TURN_PIDF[0] = SmartDashboard.getNumber("Drive Turn P", RobotMap.DRIVE_TURN_PIDF[0]);
        RobotMap.DRIVE_TURN_PIDF[1] = SmartDashboard.getNumber("Drive Turn I", RobotMap.DRIVE_TURN_PIDF[1]);
        RobotMap.DRIVE_TURN_PIDF[2] = SmartDashboard.getNumber("Drive Turn D", RobotMap.DRIVE_TURN_PIDF[2]);
        RobotMap.DRIVE_TURN_PIDF[3] = SmartDashboard.getNumber("Drive Turn F", RobotMap.DRIVE_TURN_PIDF[3]);

        pidController.setPID(RobotMap.DRIVE_TURN_PIDF[0], RobotMap.DRIVE_TURN_PIDF[1], 
            RobotMap.DRIVE_TURN_PIDF[2], RobotMap.DRIVE_TURN_PIDF[3]);
    }

    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }
}