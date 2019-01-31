package frc.robot;
import edu.wpi.first.wpilibj.*;

import frc.subsystems.*;

public class Robot extends TimedRobot {
    // Allocates variable for relevant objects
    CargoIntake cargoIntake;
    XboxController operatorController;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
	    // Instantiates relevant objects
        cargoIntake = CargoIntake.getInstance();
        operatorController = new XboxController(RobotMap.OPERATOR_CONTROLLER);
        /*
        // Creates SmartDashboard Tabs 
        cargoIntake.telemetry();
        cargoIntake.setConstantTuning();
        */
    
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
        /*
    	cargoIntake.telemetry(); // Sends diagnostics to SmartDashboard
	    cargoIntake.getConstantTuning(); // Assigns constants to values retrieved from SmartDashboard
        */
        
        if(operatorController.getAButton()) {
            cargoIntake.shoot();
        } else {
            cargoIntake.intake();
        }
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
