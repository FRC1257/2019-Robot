package frc.drivetrain;

import java.util.StringJoiner;
import frc.drivetrain.DriveTrain;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class IntegralDrive extends DifferentialDrive {

  public static final double kDefaultQuickStopThreshold = 0.2;
  public static final double kDefaultQuickStopAlpha = 0.1;

    private static int instances;

    private final SpeedController m_leftMotor;
    private final SpeedController m_rightMotor;

    private DriveTrain driveTrain;

    private double m_quickStopThreshold = kDefaultQuickStopThreshold;
    private double m_quickStopAlpha = kDefaultQuickStopAlpha;
    private double m_quickStopAccumulator;
    private double m_rightSideInvertMultiplier = -1.0;
    private boolean m_reported;


    public IntegralDrive(SpeedController leftMotor, SpeedController rightMotor) {
        super(leftMotor, rightMotor);
        verify(leftMotor, rightMotor);
        m_leftMotor = leftMotor;
        m_rightMotor = rightMotor;
        addChild(m_leftMotor);
        addChild(m_rightMotor);
        instances++;
        setName("IntegralDrive", instances);
      }

      @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
      private void verify(SpeedController leftMotor, SpeedController rightMotor) {
        if (leftMotor != null && rightMotor != null) {
          return;
        }
        StringJoiner joiner = new StringJoiner(", ");
        if (leftMotor == null) {
          joiner.add("leftMotor");
        }
        if (rightMotor == null) {
          joiner.add("rightMotor");
        }
        throw new NullPointerException(joiner.toString());
      }

      // Override acradeDrive, implementing closed-loop
      @Override
      @SuppressWarnings("ParameterName")
    public void arcadeDrive(double xSpeed, double zRotation, boolean squareInputs) { 
        DriveTrain driveTrain;
        driveTrain = DriveTrain.getInstance();

        if (!m_reported) {
        HAL.report(tResourceType.kResourceType_RobotDrive, 2,
                 tInstances.kRobotDrive2_DifferentialArcade);
        m_reported = true;
        }

        xSpeed = limit(xSpeed);
        xSpeed = applyDeadband(xSpeed, m_deadband);

        zRotation = limit(zRotation);
        zRotation = applyDeadband(zRotation, m_deadband);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        if (squareInputs) {
        xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
        zRotation = Math.copySign(zRotation * zRotation, zRotation);
        }

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

        if (xSpeed >= 0.0) {
        // First quadrant, else second quadrant
        if (zRotation >= 0.0) {
            leftMotorOutput = maxInput;
            rightMotorOutput = xSpeed - zRotation;
        } else {
            leftMotorOutput = xSpeed + zRotation;
            rightMotorOutput = maxInput;
        }
        } else {
        // Third quadrant, else fourth quadrant
        if (zRotation >= 0.0) {
            leftMotorOutput = xSpeed + zRotation;
            rightMotorOutput = maxInput;
        } else {
            leftMotorOutput = maxInput;
            rightMotorOutput = xSpeed - zRotation;
        }
        }

        // This is awful code, I'll try to come up with something else.
        // This sends these outputs to a PID loop in DriveTrain, where the closed-loop actually happens.
        driveTrain.setSetPIDlr(limit(leftMotorOutput * m_maxOutput), limit(rightMotorOutput * m_maxOutput * m_rightSideInvertMultiplier));

        feed();
    }
}