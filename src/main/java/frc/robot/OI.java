package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.util.SnailController;


public class OI {
/**
* Contains all of the mappings for controls for our robot
*     */
private static OI instance = null;
public SnailController driveController;
public SnailController operatorController;
private OI() {
    driveController = new SnailController(RobotMap.CONTROLLER_DRIVE_PORT);
    operatorController = new SnailController(RobotMap.CONTROLLER_OPERATOR_PORT);
}
public static OI getInstance() {
    if (instance == null) {
        instance = new OI();
    }
    return instance;
}
public boolean getClimbPhaser()//Button for phase changer
{
    return operatorController.getAButtonPressed();
}
public boolean getFrontChanger()//changes the front
{
    return operatorController.getXButtonPressed();
}
public boolean getBackChanger()//changes the back
{
    return operatorController.getYButtonPressed();
}
public double getClimbMotorSpeed()//measures motor speed
{
    return operatorController.getY(Hand.kLeft);
}


}