package frc.util;

import edu.wpi.first.networktables.*;
import java.util.ArrayList;

public class TestTracker{
    public ArrayList<Double> DistanceToArea;
    public boolean leftStickPressed;
    public boolean rightStickPressed;

    public TestTracker(){
        DistanceToArea = new ArrayList<Double>();
        leftStickPressed = false;
        rightStickPressed = false;
    }

    public void addDistancePercent(NetworkTable table){ // Store values
        NetworkTableEntry taE = table.getEntry("ta");
        this.DistanceToArea.add(taE.getDouble(0));
        System.out.println(this.DistanceToArea.size());
    }	    

    public void printDistancePercent(){ // Prints out values
        for(int i = 0; i < this.DistanceToArea.size() - 1; i++){
            System.out.print(this.DistanceToArea.get(i) + ", ");
        }
        System.out.println(this.DistanceToArea.get(this.DistanceToArea.size() - 1));
    }

}