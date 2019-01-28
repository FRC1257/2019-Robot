package frc.util;

import edu.wpi.first.wpilibj.XboxController;

// Xbox controller optimized for our drive team.

public class SnailController extends XboxController {

	public SnailController(int port) {
		super(port);
	}

	

	/*
	 * Controls: If they press A, use single stick arcade with the left joystick
	 * 
	 * If they press the left bumper, use the left joystick for forward and backward
	 * motion and the right joystick for turning
	 * 
	 * If they press the right bumper, use the right joystick for forward and
	 * backward motion and the left joystick for turning
	 */

	/**
	 * Gets the forward speed of the controller (for {@code arcadeDrive}) based on the driver configuration.
	 * @return The forward speed ({@code xSpeed}) of the controller.
	 */
	public double getForwardSpeed() {
		if (getAButton())
			return -getY(Hand.kLeft);
		else if (getBumper(Hand.kLeft))
			return -getY(Hand.kLeft);
		else if (getBumper(Hand.kRight))
			return -getY(Hand.kRight);
		else
			return 0;
	}
	/**
	 * Gets the turn speed of the controller (for {@code arcadeDrive}) based on the driver configuration.
	 * @return The turn speed ({@code zRotation}) of the controller.
	 */
	public double getTurnSpeed() {
		if (getAButton())
			return getX(Hand.kLeft);
		else if (getBumper(Hand.kLeft))
			return getX(Hand.kRight);
		else if (getBumper(Hand.kRight))
			return getX(Hand.kLeft);
		else
			return 0;
	}
}