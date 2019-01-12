package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climb {
    
    private static Climb instance = null;

    private Climb() {

    }
    
    

    public static Climb getInstance() {
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }
}