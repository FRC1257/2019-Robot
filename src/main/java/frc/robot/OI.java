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
public boolean getReset()//gets motor speed
{
    return operatorController.getBButtonPressed();
}
public double getClimbMotorSpeed()//gets motor speed
{
    return operatorController.getY(Hand.kLeft);
}
public static OI getInstance() {
    if (instance == null) {
        instance = new OI();
    }
    return instance;
}
}