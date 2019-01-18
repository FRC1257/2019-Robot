package frc.robot;
import edu.wpi.first.wpilibj.*;

import frc.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    CargoIntake cargoIntake;
    XboxController operatorController;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        cargoIntake = CargoIntake.getInstance();
        operatorController = new XboxController(RobotMap.OPERATOR_CONTROLLER);
        
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
            cargoIntake.shoot();
        }
        if(operatorController.getBButton())
        {
            cargoIntake.intake();
        }
        SmartDashboard.putNumber("Distance to Cargo", cargoIntake.getDistanceToCargo());
        SmartDashboard.putNumber("Intake Speed", RobotMap.CARGON_INTAKE_SPEED);
		SmartDashboard.putNumber("Outake Speed", RobotMap.CARGO_OUTTAKE_SPEED);
		SmartDashboard.putNumber("Point Of No Return", RobotMap.CARGO_PONR);

        cargoIntake.pointOfNoReturn(operatorController);

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
