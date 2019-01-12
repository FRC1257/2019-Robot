package frc.drivetrain;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.*;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveTrain {
    private static DriveTrain instance = null;

    private CANSparkMax flDrive;
    private CANSparkMax frDrive;
    private CANSparkMax blDrive;
    private CANSparkMax brDrive; 

    private SpeedControllerGroup lDrive;
    private SpeedControllerGroup rDrive;

    private IntegralDrive driveTrain;

    private DriveTrain() {
        flDrive = new CANSparkMax(RobotMap.motors[0], MotorType.kBrushless);
        frDrive = new CANSparkMax(RobotMap.motors[1], MotorType.kBrushless);
        blDrive = new CANSparkMax(RobotMap.motors[2], MotorType.kBrushless);
        brDrive = new CANSparkMax(RobotMap.motors[3], MotorType.kBrushless);

        lDrive = new SpeedControllerGroup(flDrive, blDrive);
        rDrive = new SpeedControllerGroup(frDrive, brDrive);

        driveTrain = new IntegralDrive(lDrive, rDrive);
    }

    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }
}