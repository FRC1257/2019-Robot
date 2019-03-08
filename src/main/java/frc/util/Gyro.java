package frc.util;

import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * Wrapper class for the navX-MXP and ADXRS450_Gyro
 * Mostly utilizes the navX-MXP, but uses the ADXRS450 as backup if it disconnects
 */

public class Gyro {

    private static Gyro instance = null;

    private AHRS navx;
    private double resetRoll;
    private double resetPitch;

    private ADXRS450_Gyro gyro;

    private Gyro() {
        navx = new AHRS(Port.kMXP);
        resetRoll = 0;
        resetPitch = 0;

        gyro = new ADXRS450_Gyro();
    }

    /**
     * Gets the current yaw angle.
     * 
     * @return The angle in degrees.
     */
    public double getYawAngle() {
        return navx.getYaw();
    }

    /**
     * Gets the current roll angle.
     * 
     * @return The angle in degrees.
     */
    public double getRollAngle() {
        return navx.getRoll() - resetRoll;
    }

    /**
     * Gets the current pitch angle.
     * 
     * @return The angle in degrees.
     */
    public double getPitchAngle() {
        return navx.getPitch() - resetPitch;
    }
    /**
     * Sets the current yaw angle to "0".
     */
    public void zeroYawAngle() {
        navx.zeroYaw();
    }

    /**
     * Sets the current roll angle to "0".
     */
    public void zeroRollAngle() {
        resetRoll = getRollAngle();
    }

    /**
     * Sets the current pitch angle to "0".
     */
    public void zeroPitchAngle() {
        resetPitch = getPitchAngle();
    }

    /**
     * Gets the current rotation of the robot.
     * 
     * @return The angle in degrees.
     */
    public double getRobotAngle() {
        return getYawAngle();
    }

    /**
     * Gets the current tilt of the robot while climbing.
     * 
     * @return THe angle in degrees
     */
    public double getClimbTiltAngle() {
        if(navXConnected()) {
            return getRollAngle();
        }
        else {
            return gyro.getAngle();
        }
    }

    /**
     * Sets the current rotation of the robot to "0".
     */
    public void zeroRobotAngle() {
        zeroYawAngle();
    }

    /**
     * Sets the current tilt of the robot to "0".
     */
    public void zeroClimbTiltAngle() {
        zeroRollAngle();
        gyro.reset();
    }

    public boolean navXConnected() {
        return navx.isConnected();
    }

    /**
     * Displays the yaw angle on {@code SmartDashboard}.
     */
    public void displayAngle() {
        SmartDashboard.putNumber("Yaw Angle", getYawAngle());
        SmartDashboard.putNumber("Roll Angle", getRollAngle());
        SmartDashboard.putNumber("Pitch Angle", getPitchAngle());

        SmartDashboard.putNumber("Robot Angle", getRobotAngle());
        SmartDashboard.putNumber("Climb Tilt Angle", getClimbTiltAngle());
        SmartDashboard.putBoolean("Gyro Connected", navXConnected());
    }

    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }
        return instance;
    }
}