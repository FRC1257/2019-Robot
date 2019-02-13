package frc.util;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.RobotMap;

// Xbox controller optimized for our drive team.

public class SnailController extends XboxController {

	public SnailController(int port) {
		super(port);
	}

	@Override
	public double getY(Hand hand) {
		return applyDeadband(-super.getY(hand));
	}

	@Override
	public double getX(Hand hand) {
		return applyDeadband(super.getX(hand));
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
	
	public double getForwardSpeed() {
		if (getAButton())
			return getY(Hand.kLeft);
		else if (getBumper(Hand.kLeft))
			return getY(Hand.kLeft);
		else if (getBumper(Hand.kRight))
			return getY(Hand.kRight);
		else
			return 0;
	}

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

	public double applyDeadband(double number) {
        if(Math.abs(number) < RobotMap.CONTROLLER_DEADBAND) {
            return 0;
        }
        return number;
    }

    public static double squareInput(double number) {
        // Use abs to prevent the sign from being cancelled out
        return Math.abs(number) * number;
	}

	public boolean getDPadUp() {
		if (getPOV(RobotMap.CONTROLLER_POV) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getDPadRight() {
		if (getPOV(RobotMap.CONTROLLER_POV) == 90) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getDPadDown() {
		if (getPOV(RobotMap.CONTROLLER_POV) == 180) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getDPadLeft() {
		if (getPOV(RobotMap.CONTROLLER_POV) == 270) {
			return true;
		}
		else {
			return false;
		}
	}
}