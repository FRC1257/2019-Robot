package frc.robot;

import edu.wpi.first.wpilibj.*;

public class Robot extends TimedRobot {
    Climb climb;
    XboxController controller;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
    climb = new Climb();
    climb.reset();
    controller= new XboxController(1);
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
    boolean FrontOn = false;
    boolean BackOn = false;
    if(controller.getAButton && FrontOn==false)
    {
    climb.frontforward();
    FrontOn=true;
    }
    if(controller.getAButton && FrontOn==true)
    {
    climb.frontreverse();
    FrontOn=false;
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
