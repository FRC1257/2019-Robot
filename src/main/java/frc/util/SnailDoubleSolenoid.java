package frc.util;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SnailDoubleSolenoid extends DoubleSolenoid {

    /**
     * Double solenoid class to prevent refiring
     */

    public SnailDoubleSolenoid(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }
    
    public SnailDoubleSolenoid(int moduleNumber, int forwardChannel, int reverseChannel) {
        super(moduleNumber, forwardChannel, reverseChannel);
    }

    @Override
    public void set(Value value) {
        if (value != get()) {
            super.set(value);
        }
    }
}