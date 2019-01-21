package frc.subsystems;

import frc.robot.*;

import com.revrobotics.*;

public class FlakeMin extends CANSparkMax {

    public CANPIDController PID;
    public CANEncoder Encoder;
    private double currentSpeed;
    private double Speed;
    private double Distance;

    public FlakeMin(int deviceID, CANSparkMaxLowLevel.MotorType type, boolean left) {
        super(deviceID, type);
            if(left) {
                yourPIDfunctionsucks(RobotMap.PID_LEFT[0], RobotMap.PID_LEFT[1], 
                    RobotMap.PID_LEFT[2], RobotMap.PID_LEFT[3]);
            } else {
                yourPIDfunctionsucks(RobotMap.PID_RIGHT[0], RobotMap.PID_RIGHT[1], 
                    RobotMap.PID_RIGHT[2], RobotMap.PID_RIGHT[3]);
            }
        currentSpeed = 0.0;
        Encoder = getEncoder();
    }

    private double updateInput(double currentInput, double targetInput) {
        double error;
        error = targetInput - currentInput;
        currentInput += RobotMap.P_WASHOUT * error;
        return currentInput;
    }

    @Override
    public void set(double speed) {
        PID.setReference(RobotMap.NEO_CONSTS[2] * updateInput(currentSpeed, speed), ControlType.kVelocity);
        currentSpeed = updateInput(currentSpeed, speed);
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
    public double getSpeed() {
        Speed = Encoder.getVelocity();
        return Speed;
    }
    public double getPlace() {
        Distance = Encoder.getPosition();
        return Distance;
    }
    
    public double getPlaceInches() {
        return getPlace() * RobotMap.diameter * RobotMap.pi;
    }

    public double getPlaceFeet() {
        return getPlaceInches() / 12;
    }

    public double getSpeedFeet() {
        return getSpeed() * RobotMap.diameter * RobotMap.pi / 12;
    }

    public double getSpeedInches() {
        return getSpeedFeet() / 12;
    }

    public void resetVelocity() {
        double temp = getSpeed();
        Speed = Speed - temp;
    }

    public void resetPlace() {
        double temp = getPlace();
        Distance = Distance - temp;
    }

    public void resetAll() {
        resetVelocity();
        resetPlace();
    }
}