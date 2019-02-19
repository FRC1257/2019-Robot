package frc.util;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

/**
 * Helper class for the Spark Max PID Controller
 * When the setPIDPosition() function is called, it sets up a notifier to monitor when the movement is complete
 * The isRunning() command can then be used to check if the action is complete
 * 
 * Three main configuration variables:
 *  updatePeriod - how often the PID loop is checked in seconds
 *  tolerance - how close the PID loop can be in revolutions
 *  thresholdTime - how long the PID loop must be within the threshold in seconds
 */

public class SnailSparkMaxPIDF {

    private CANSparkMax motor;
    private CANEncoder encoder;
    private CANPIDController pidController;

    private Notifier notifier; // looper for updating PID loop for arm
    private double currentPIDSetpoint; // current target position of arm
    private double pidTime; // the timestamp for when the PID enters the tolerance range
    private boolean running; // whether or not the PID loop is currently running

    private double updatePeriod; // how often in seconds the PID will be checked to see if it is in the tolerance
    private double tolerance; // how close the PID has to be before it finishes
    private double thresholdTime; // how long the PID has to be within the tolerance before it finishes

    public SnailSparkMaxPIDF(CANSparkMax sparkMax, double[] PIDF, double updatePeriod, double tolerance,
            double thresholdTime) {
        motor = sparkMax;
        encoder = sparkMax.getEncoder();
        pidController = sparkMax.getPIDController();

        setPIDF(PIDF);
        setOutputRange(-1.0, 1.0);

        this.updatePeriod = updatePeriod;
        this.tolerance = tolerance;
        this.thresholdTime = thresholdTime;

        notifier = new Notifier(this::updatePID);
        notifier.stop();
    }

    public void reset() {
        currentPIDSetpoint = 0;
        pidTime = -1;
        running = false;
    }

    public void setPIDPosition(double value) {
        pidController.setReference(value, ControlType.kPosition);
        currentPIDSetpoint = value;
        pidController.setIAccum(0);
        notifier.startPeriodic(updatePeriod);
    }

    private void updatePID() {
        running = true;
        pidController.setReference(currentPIDSetpoint, ControlType.kPosition);

        // Check if the encoder's position is within the tolerance
        if (Math.abs(encoder.getPosition() - currentPIDSetpoint) < tolerance) {
            // If this is the first time it has been detected, then update the timestamp
            if (pidTime == -1) {
                pidTime = Timer.getFPGATimestamp();
            }

            // Check if the encoder's position has been inside the tolerance for long enough
            if ((Timer.getFPGATimestamp() - pidTime) >= thresholdTime) {
                breakPID();
            }
        } else {
            pidTime = -1;
        }
    }

    // Breaks the PID loop
    public void breakPID() {
        notifier.stop();
        running = false;
        pidTime = -1;
    }

    /*
     * Getters
     * 
     * All getters that return the various variables used by this class
     * 
     */

    public double getP() {
        return pidController.getP();
    }

    public double getI() {
        return pidController.getI();
    }

    public double getD() {
        return pidController.getD();
    }

    public double getF() {
        return pidController.getFF();
    }

    public double[] getPIDF() {
        return new double[] { getP(), getI(), getD(), getF() };
    }

    public double getSetpoint() {
        return currentPIDSetpoint;
    }

    public double getPIDTime() {
        return pidTime;
    }

    public double getPIDTimeElapsed() {
        if (pidTime == -1)
            return -1;
        else
            return Timer.getFPGATimestamp() - pidTime;
    }

    public boolean isRunning() {
        return running;
    }

    public CANSparkMax getSparkMax() {
        return motor;
    }

    public CANEncoder getEncoder() {
        return encoder;
    }

    // Returns the Spark Max PID Controller object for custom settings
    public CANPIDController getPIDController() {
        return pidController;
    }

    /*
     * Configuration
     * 
     * All configuration functions used to tune various aspects of the class
     * 
     */

    public void setOutputRange(double minOutput, double maxOutput) {
        pidController.setOutputRange(minOutput, maxOutput);
    }

    public void setP(double p) {
        pidController.setP(p);
    }

    public void setI(double i) {
        pidController.setI(i);
    }

    public void setD(double d) {
        pidController.setD(d);
    }

    public void setF(double f) {
        pidController.setFF(f);
    }

    public void setPIDF(double[] PIDF) {
        pidController.setP(PIDF[0]);
        pidController.setI(PIDF[1]);
        pidController.setD(PIDF[2]);
        pidController.setFF(PIDF[3]);
    }

    // Sets the time in between loops of the notifier to check if the PID has
    // finished
    public void setUpdatePeriod(double updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    // Sets how close the PID controller can be to the setpoint before it can
    // declare that it is finished
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    // Sets how long the PID controller has to settle at the setpoint before it cna
    // declare that it is finished
    public void setThresholdTime(double thresholdTime) {
        this.thresholdTime = thresholdTime;
    }

    /*
     * Smart Dashboard
     * 
     * All functions used for outputting to Smart Dashboard and retrieving data Used
     * for debugging and tuning
     * 
     */

    // Outputs debugging value to SmartDashboard
    public void outputValues() {
        outputValues("");
    }

    // Outputs debugging value to SmartDashboard
    public void outputValues(String prefix) {
        SmartDashboard.putBoolean(prefix + "PID Active", running);
        SmartDashboard.putNumber(prefix + "PID Setpoint", currentPIDSetpoint);
    }

    // Sends values from the array to SmartDashboard for later tuning
    public void setConstantTuning(double[] PIDF) {
        setConstantTuning("", PIDF);
    }

    // Sends values from the array to SmartDashboard for later tuning
    public void setConstantTuning(String prefix, double[] PIDF) {
        SmartDashboard.putNumber(prefix + "P", PIDF[0]);
        SmartDashboard.putNumber(prefix + "I", PIDF[1]);
        SmartDashboard.putNumber(prefix + "D", PIDF[2]);
        SmartDashboard.putNumber(prefix + "F", PIDF[3]);
    }

    // Updates the given array and the current pid controller with values from
    // SmartDashboard
    public void getConstantTuning(double[] PIDF) {
        getConstantTuning("", PIDF);
    }

    // Updates the given array and the current pid controller with values from
    // SmartDashboard
    public void getConstantTuning(String prefix, double[] PIDF) {
        if (PIDF[0] != SmartDashboard.getNumber(prefix + "P", PIDF[0])) {
            PIDF[0] = SmartDashboard.getNumber(prefix + "P", PIDF[0]);
            pidController.setP(PIDF[0]);
        }
        if (PIDF[1] != SmartDashboard.getNumber(prefix + "I", PIDF[1])) {
            PIDF[1] = SmartDashboard.getNumber(prefix + "I", PIDF[1]);
            pidController.setP(PIDF[1]);
        }
        if (PIDF[2] != SmartDashboard.getNumber(prefix + "D", PIDF[2])) {
            PIDF[2] = SmartDashboard.getNumber(prefix + "D", PIDF[2]);
            pidController.setP(PIDF[2]);
        }
        if (PIDF[3] != SmartDashboard.getNumber(prefix + "F", PIDF[3])) {
            PIDF[3] = SmartDashboard.getNumber(prefix + "F", PIDF[3]);
            pidController.setP(PIDF[3]);
        }
    }
}