package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.subsystems.DriveTrain;
import frc.util.ConsolePrinter;

public class Robot extends TimedRobot {
	
	private DriveTrain drive;
	private OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		drive = DriveTrain.getInstance();
		oi = OI.getInstance();
		ConsolePrinter.getInstance();
		ConsolePrinter.setRate(100);
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
	 * This function is called once each time when the robot enters test mode.
	 */
	@Override
	public void testInit() {

	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		double xSpeed = 0.0;
		double zRotation = 0.0;

		if(oi.getDriveToggleReverse()) drive.toggleReverse();

		drive.drive(xSpeed, zRotation);
		drive.getFLDrive().outputValues();
		drive.getFRDrive().outputValues();

		if(oi.getDriveTestFL()) {
			drive.getFRDrive().follow(drive.getFLDrive());
			drive.getFLDrive().set(0.6);
			drive.getFLDrive().outputValues();
		}
		else if(oi.getDriveTestFR()) {
			drive.getFLDrive().follow(drive.getFLDrive());
			drive.getFRDrive().set(0.6);
			drive.getFRDrive().outputValues();
		}

		ConsolePrinter.putNumber("Right voltage", () -> {return drive.getFRDrive().getVoltage();}, true, true);
		ConsolePrinter.putNumber("Right RPM", () -> {return drive.getFRDrive().getVoltage();}, true, true);
		ConsolePrinter.putNumber("Left voltage", () -> {return drive.getFLDrive().getVoltage();}, true, true);
		ConsolePrinter.putNumber("Left RPM", () -> {return drive.getFLDrive().getVoltage();}, true, true);
		ConsolePrinter.startThread();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
