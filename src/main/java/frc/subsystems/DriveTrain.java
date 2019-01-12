package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.*;

public class DriveTrain {
    private static DriveTrain instance = null;

    private PWMTalonSRX flDrive;
    private PWMTalonSRX frDrive;
    private PWMTalonSRX blDrive;
    private PWMTalonSRX brDrive; 

    private SpeedControllerGroup lDrive;
    private SpeedControllerGroup rDrive;

    private DifferentialDrive driveTrain;

    private DriveTrain() {
        flDrive = new PWMTalonSRX(RobotMap.motors[0]);
        frDrive = new PWMTalonSRX(RobotMap.motors[1]);
        blDrive = new PWMTalonSRX(RobotMap.motors[2]);
        brDrive = new PWMTalonSRX(RobotMap.motors[3]);

        lDrive = new SpeedControllerGroup(flDrive, blDrive);
        rDrive = new SpeedControllerGroup(frDrive, brDrive);

        driveTrain = new DifferentialDrive(lDrive, rDrive);
    }

    private double calculateV() {
        double v = 0.0;
        return v;
    }

    public static DriveTrain getInstance() {
        if(instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }
}