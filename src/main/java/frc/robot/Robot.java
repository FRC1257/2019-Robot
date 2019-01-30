package frc.robot;

import frc.subsystems.*;
import frc.robot.RobotMap;
import frc.util.TestTracker;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

    XboxController Controller;
    
    @Override
    public void robotInit() {
        Controller = new XboxController(0);
        SmartDashboard.putNumber("kP", RobotMap.TURN_CORRECT_KP);
        SmartDashboard.putNumber("min_command", RobotMap.TURN_CORRECT_MIN_COMMAND); 
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
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

        if(Controller.getTriggerAxis(GenericHID.Hand.kLeft) > 0.5){ //If left trigger pressed and a target on screen then turn to it
            // DriveTrain.arcadeDrive(0, Vision.turnCorrect(table)); //Add when actual drivetrain exists
        }
    }

    /**
     * This function is called once each time when the robot enters test mode.
     */

    TestTracker visionTracker; //Purely for test. Never used again

    @Override
    public void testInit() {
        visionTracker = new TestTracker();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        
        if(Controller.getStickButtonPressed(GenericHID.Hand.kRight) && visionTracker.rightStickPressed == false){
            visionTracker.addDistancePercent(table);
            visionTracker.rightStickPressed = true;
        }
        else if(Controller.getStickButtonReleased(GenericHID.Hand.kRight) && visionTracker.rightStickPressed == true){
            visionTracker.rightStickPressed = false;
        }
        if(Controller.getStickButtonPressed(GenericHID.Hand.kLeft) && visionTracker.leftStickPressed == false){
            visionTracker.printDistancePercent();
            visionTracker.leftStickPressed = true;
        }
        else if(Controller.getStickButtonReleased(GenericHID.Hand.kLeft) && visionTracker.leftStickPressed == true){
            visionTracker.leftStickPressed = false;
        }
    }
}
