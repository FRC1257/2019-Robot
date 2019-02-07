package frc.util;

import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C.Port;

public class Gyro {
    private AHRS navx;
    private static Gyro instance = null;

    private Gyro() {
        navx = new AHRS(Port.kMXP);
    }
    
    /**
     * Gets the current yaw angle.
     * @return The angle in degrees.
     */
    public double getAngle() {
        return navx.getYaw();
    }

    /**
     * Sets the current yaw angle to "0".
     */
    public void zeroAngle() {
        navx.zeroYaw();
    }
    
    /**
     * Displays the yaw angle on {@code SmartDashboard}.
     */
    public void displayAngle() {
        SmartDashboard.putNumber("Angle", getAngle());
    }

    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }
        return instance;
    }
}