package frc.util.snail_vision;

import edu.wpi.first.networktables.*;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight{

    public Limelight(){}

    // Limelight ONLY functions

    public static void toggleLimelightScreenshot(){ // Takes 2 screenshots per second
        NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
        if(Table.getEntry("snapshot").getDouble(0) == 0){ // If the camera is not taking screenshots currently
            Table.getEntry("snapshot").setNumber(1);
        }
        else if(Table.getEntry("snapshot").getDouble(0) == 1){ // If the camera is taking screenshots currently
            Table.getEntry("snapshot").setNumber(0);
        }
    }

    public static void turnOffLimelight(){
        NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
        if(Table.getEntry("ledMode").getDouble(0) != 1){
            Table.getEntry("ledMode").setNumber(1);
        }
    }

    public static void turnOnLimelight(){
        NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
        if(Table.getEntry("ledMode").getDouble(0) != 3){
            Table.getEntry("ledMode").setNumber(3);
        }
    }

    public static void blinkLimelight(){
        NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
        if(Table.getEntry("ledMode").getDouble(0) != 2){
            Table.getEntry("ledMode").setNumber(2);
        }
    }

    public static void toggleLimelightMode(){ // 0 = vision processing 1 = drive camera
        NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
        if(Table.getEntry("camMode").getDouble(0) == 0){
            Table.getEntry("camMode").setNumber(1);
        }
        else if(Table.getEntry("camMode").getDouble(0) == 1){
            Table.getEntry("camMode").setNumber(0);
        }
    }

}