package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class HatchIntake {
    
    private static HatchIntake instance = null;

    private HatchIntake() {

    }
    
    public void pickup(boolean loadingStation) {

    }

    public void extend() {

    }

    public void retract() {
        
    }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
