package frc.util;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author James
 *
 */
public class LoggableBoolean implements Loggable {
	private Supplier<Boolean> value;

	public LoggableBoolean(Supplier<Boolean> value) {
		this.value = value;
	}

	/**
	 * Send this value to the dashboard.
	 * @param key the key associated with this value to send to the dashboard.
	 */
	@Override
	public void put(String key) {
		SmartDashboard.putBoolean(key, value.get());
	}

	/* (non-Javadoc)
	 * @see org.team2168.robot.utils.consoleprinter.Loggable#valueToString()
	 */
	@Override
	public String valueToString() {
		return value.get().toString();
	}
}