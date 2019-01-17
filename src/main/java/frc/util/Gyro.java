package frc.util;

import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.I2C.Port;

public class Gyro {
    private AHRS navx;
    private static Gyro instance = null;

    private Gyro() {
        navx = new AHRS(Port.kMXP);
    }

    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }
        return instance;
    }
}