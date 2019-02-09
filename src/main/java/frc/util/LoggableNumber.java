package frc.util;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author James
 *
 */
public class LoggableNumber implements Loggable {
	private Supplier<Double> value;
	
	public LoggableNumber(Supplier<Double> value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see org.team2168.robot.utils.consoleprinter.Loggable#put(java.lang.String)
	 */
	@Override
	public void put(String key) {
		SmartDashboard.putNumber(key, value.get());
	}

	/* (non-Javadoc)
	 * @see org.team2168.robot.utils.consoleprinter.Loggable#valueToString()
	 */
	@Override
	public String valueToString() {
		return value.get().toString();
	}
}