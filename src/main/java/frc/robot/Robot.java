package frc.robot;
import edu.wpi.first.wpilibj.*;

import frc.subsystems.*;

public class Robot extends TimedRobot {
    
    CargoIntake cargoIntake;
    XboxController operatorController;
    OI oi;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        cargoIntake = CargoIntake.getInstance();
        operatorController = new XboxController(RobotMap.CONTROLLER_OPERATOR_PORT);

        cargoIntake.setConstantTuning();
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
        // Assign constants to values retrieved from Smart Dashboard
	    cargoIntake.getConstantTuning();
        
        // Constantly intake unless shooting
        if(oi.getShootButton()) {
            cargoIntake.shoot();
        }
        else {
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
