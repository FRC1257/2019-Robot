package frc.robot;
import edu.wpi.first.wpilibj.*;

import frc.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    CargoIntake intake;
    XboxController operatorController;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        intake = CargoIntake.getInstance();
        operatorController = new XboxController(RobotMap.OPERATOR_CONTROLLER);
        SmartDashboard.putString("Auto Status", "Hi");
		SmartDashboard.putNumber("Auto Delay", 5);
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
        if(operatorController.getAButton())
        {
            intake.shoot();
        }
        if(operatorController.getBButton())
        {
            intake.intake();
        }
        SmartDashboard.putNumber("Distance to Cargo", intake.getDistanceToCargo());

        intake.pointOfNoReturn(operatorController);

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
