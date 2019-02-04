package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.subsystems.*;

public class Robot extends TimedRobot {

	Climb climb;
	OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		climb = Climb.getInstance();
		oi = OI.getInstance();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {

	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		// Testing
		if(oi.getClimbBackToggle()) climb.toggleBack();
		if(oi.getClimbFrontToggle()) climb.toggleFront();

		climb.climbDrive(oi.getClimbDriveSpeed());
		if(oi.getClimbAdvance()) climb.advanceClimb(false);
		if(oi.getClimbReset()) climb.reset();
		climb.outputValues();
	}

	/**
	 * This function is called once each time when the robot enters test mode.
	 */
	@Override
	public void testInit() {

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
