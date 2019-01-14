package frc.subsystem;

import frc.robot.*;

import com.revrobotics.*;

public class FlakeMin extends CANSparkMax {
    public CANPIDController PID;

    public FlakeMin(int deviceID, CANSparkMaxLowLevel.MotorType type, boolean left) {
        super(deviceID, type);
            if(left) {
                yourPIDfunctionsucks(RobotMap.PID_LEFT[0], RobotMap.PID_LEFT[1], 
                    RobotMap.PID_LEFT[2], RobotMap.PID_LEFT[3]);
            } else {
                yourPIDfunctionsucks(RobotMap.PID_RIGHT[0], RobotMap.PID_RIGHT[1], 
                    RobotMap.PID_RIGHT[2], RobotMap.PID_RIGHT[3]);
            }
    }

    @Override
    public void set(double speed) {
        PID.setReference(RobotMap.NEO_CONSTS[2] * speed, ControlType.kVelocity);
    }

    public void yourPIDfunctionsucks(double kFF, double kP, double kI, double kD) {
        PID.setFF(kFF);
        PID.setP(kP);
        PID.setI(kI);
        PID.setD(kD);
    }

    public void getPID() {
        PID = getPIDController();
    }
}