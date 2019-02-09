package frc.util;

/**
 * A helper class used by ConsolePrinter that identifies whether a string
 *   should be logged file, the SmartDashboard, or both.
 * @author James
 */
public interface Loggable {
	/**
	 * Send this item to the dashboard.
	 * @param key
	 */
	public void put(String key);
	
	/**
	 * 
	 * @return the string representation of the data value.
	 */
	public String valueToString();
}
