package frc.util.snail_vision;

import com.kauailabs.navx.frc.*;
import java.util.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

public class Gyro {
    
    // Gyroscope Variables
    public boolean useGyro;
    public String rotationalAxis; // Yaw - navx is flat pointing forward Pitch - navx is vertical pointing forward Roll - navx is vertical pointing sideways
    public AHRS navx;
    public double resetAngle;
    public double currentAcceleration;
    public double pastAcceleration; // 1 iteration behind
    public Timer Timer;
    public double instantaneousJerk;
    public double JERK_COLLISION_THRESHOLD; // What the jerk has to be for it to be considered a collision
    public boolean printIterationTime;
    public double JERK_CALCULATION_RATE; // seconds

    public Gyro(boolean utilizeGyro){
        useGyro = utilizeGyro;
        if(useGyro == true){
            rotationalAxis = "yaw"; // Default is yaw
            navx = new AHRS(SPI.Port.kMXP);
            pastAcceleration = 0;
            currentAcceleration = 0;
            Timer = new Timer();
            Timer.start();
            printIterationTime = false;
            JERK_CALCULATION_RATE = 0.01; // 0.01 seconds per calculation by default
        }
    }

    // Gyroscope NavX functionality - Included in SnailVision so that gyro works even if it is nowhere else in the project
    public void gyroFunctionality(SnailVision SnailVision){
        // Used for tracking the target offscreen
        if(SnailVision.TargetV.size() > 0 && SnailVision.TargetX.size() > 0){
            if(SnailVision.TargetV.get(0) == true){
                resetRotationalAngle(); // Make the front of robot's current position 0
                resetAngle -= SnailVision.TargetX.get(0); // Changes the robot's current position to the center of the target
            }
        }
        else{
            Util.printError("ArrayLists are missing values. A function is called before the SnailVision object gets values from the camera.");
        }

        // Used for jerk and collision detection
        instantaneousJerk = calculateJerk();
    }
    
    public double getRotationalAngle(){ // Angle of the robot as it rotates
        if(rotationalAxis.equals("yaw")){
            return getYawAngle(); // Even though yaw has a reset funciton it is used with resetAngle so that an origin could be set
        }
        else if(rotationalAxis.equals("roll")){
            return getRollAngle(); // resetAngle is here to act as a reset function
        }
        else if(rotationalAxis.equals("pitch")){
            return getPitchAngle(); // resetAngle is here to act as a reset function
        }
        else{
            return(0); // Just in case something breaks
        }
    }

    public void resetRotationalAngle(){
        if(rotationalAxis.equals("yaw")){
            resetAngle = navx.getYaw(); // Even though yaw has a reset funciton it is used like this so that an origin could be set
        }
        else if(rotationalAxis.equals("roll")){
            resetAngle = navx.getRoll();
        }
        else if(rotationalAxis.equals("pitch")){
            resetAngle = navx.getPitch();
        }
    }

    public double getYawAngle() {
        return (navx.getYaw() - resetAngle); // Even though yaw has a reset funciton it is used like this so that an origin could be set
    }

    public double getRollAngle() {
        return (navx.getRoll() - resetAngle);
    }
    
    public double getPitchAngle() {
        return (navx.getPitch() - resetAngle);
    }

    public double getAcceleration() {
        if(rotationalAxis.equals("yaw")){
           return(navx.getRawAccelX());
        }
        else if(rotationalAxis.equals("roll")){
            return(navx.getRawAccelY());
        }
        else if(rotationalAxis.equals("pitch")){
            return(navx.getRawAccelZ());
        }   
        else{
            return(0); // Just in case something breaks
        }
    }

    public double calculateJerk(){
        if(Timer.get() > 0.1){
            currentAcceleration = getAcceleration();
            instantaneousJerk = (pastAcceleration - currentAcceleration) / Timer.get(); // Change in accelleration = jerk
            Timer.reset();
            pastAcceleration = currentAcceleration;
        }
        return(instantaneousJerk);
    }
    
}