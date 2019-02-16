package frc.util;

import edu.wpi.first.wpilibj.Solenoid;

public class SnailSolenoid extends Solenoid {

    /**
     * Solenoid class to prevent refiring
     */

    public SnailSolenoid(int channel) {
        super(channel);
    }

    @Override
    public void set(boolean on) {
        if (on != get()) {
            super.set(on);
        }
    }
}