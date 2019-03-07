package frc.util.snail_vision;

import edu.wpi.first.wpilibj.DriverStation;

public class Util{

    public Util(){

    }
    
    // Utility functions

    public static void printError(String error){
        DriverStation.reportError("snail-vision error: " + error, true); 
        System.out.println("snail-vision error: " + error);
    }
}