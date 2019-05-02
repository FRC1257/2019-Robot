package frc.util.snail_vision;

import edu.wpi.first.networktables.*;

public class NetworkTableHelper{

    public NetworkTableHelper(){}
    
    // NetworkTable specific functions

    public static void changePipeline(NetworkTable Table, int pipeline){
        if(pipeline < 0 || pipeline > 9){
            pipeline = 0; // In case that someone tries to switch to an impossible pipeline then set it to default.
        }
        Table.getEntry("pipeline").setNumber(pipeline);
    }
}