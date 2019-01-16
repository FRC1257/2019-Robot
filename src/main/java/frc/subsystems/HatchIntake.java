package frc.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class HatchIntake {
    
    private static HatchIntake instance = null;
    DoubleSolenoid pickupSolenoid;
    DoubleSolenoid ejectSolenoid;
    
    public HatchIntake() {
        pickupSolenoid = new DoubleSolenoid(1, 2);
        ejectSolenoid = new DoubleSolenoid(3, 4);
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
