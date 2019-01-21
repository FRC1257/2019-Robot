package frc.subsystems;

import frc.robot.*;

import com.revrobotics.*;

/**
 * <h1>FlakeMin</h1>
 * This allows us to perform closed-loop drivetrain as well as other fun things.
 * @author Allen Du, Arthur Chen, Om Desai, and Nicole Giron
 * @since 2019-01-21
 */
public class FlakeMin extends CANSparkMax {

    public CANPIDController PID;
    public CANEncoder Encoder;
    private double currentSpeed;
    private double Speed;
    private double Distance;

    /**
     * Constructs a new {@code FlakeMin} object.
     * @param deviceID The port it's wired to.
     * @param type Brushed or brushless, depending on the motor.
     * @param left If it's on the left side or not.
     */
    public FlakeMin(int deviceID, CANSparkMaxLowLevel.MotorType type, boolean left) {
        super(deviceID, type);
        getPID();
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

    /**
     * Used for a washout filter. It takes an input and runs a P loop on it.
     * @param currentInput The actual, empirical input.
     * @param targetInput The desired input.
     */
    private double updateInput(double currentInput, double targetInput) {
        double error;
        error = targetInput - currentInput;
        currentInput += RobotMap.P_WASHOUT * error;
        return currentInput;
    }

    @Override
    /**
     * Runs PID to set the motor at a constant speed; closed-loop velocity.
     * @param speed The desired speed.
     */
    public void set(double speed) {
        PID.setReference(RobotMap.NEO_CONSTS[2] * updateInput(currentSpeed, speed), ControlType.kVelocity);
        currentSpeed = updateInput(currentSpeed, speed);
    }

    /**
     * Sets the controller's PID more efficiently than the default functions.
     * @param kFF
     * @param kP
     * @param kI
     * @param kD
     */
    public void yourPIDfunctionsucks(double kFF, double kP, double kI, double kD) {
        PID.setFF(kFF);
        PID.setP(kP);
        PID.setI(kI);
        PID.setD(kD);
    }

    /**
     * Creates the PID object.
     */
    public void getPID() {
        PID = getPIDController();
    }

    /**
     * Gets the speed of the motor, in RPM, using an encoder.
     */
    public double getSpeed() {
        Speed = Encoder.getVelocity();
        return Speed;
    }

    /**
     * Gets the distance, in total revolutions, traveled by the motor using an encoder.
     */
    public double getPlace() {
        Distance = Encoder.getPosition();
        return Distance;
    }
    
    /**
     * Gets the distance traveled in inches.
     */
    public double getPlaceInches() {
        return getPlace() * RobotMap.diameter * RobotMap.pi;
    }

    /**
     * Gets the distance traveled in feet.
     */
    public double getPlaceFeet() {
        return getPlaceInches() / 12;
    }

    /**
     * Gets the speed in feet.
     */
    public double getSpeedFeet() {
        return getSpeed() * RobotMap.diameter * RobotMap.pi / 12;
    }

    /**
     * Gets the speed in inches.
     */
    public double getSpeedInches() {
        return getSpeedFeet() / 12;
    }

    /**
     * Resets the speed.
     */
    public void resetVelocity() {
        double temp = getSpeed();
        Speed = Speed - temp;
    }

    /**
     * Resets the total distance traveled.
     */
    public void resetPlace() {
        double temp = getPlace();
        Distance = Distance - temp;
    }

    /**
     * Resets the speed and distance.
     */
    public void resetAll() {
        resetVelocity();
        resetPlace();
    }
}