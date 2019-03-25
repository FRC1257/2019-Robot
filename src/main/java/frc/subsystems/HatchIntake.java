package frc.subsystems;


import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class HatchIntake {

    private static HatchIntake instance = null;

    private WPI_VictorSPX hatchIntakeMotor;

    private HatchIntake() {
        hatchIntakeMotor = new WPI_VictorSPX(RobotMap.HATCH_INTAKE_MOTOR_ID);
        hatchIntakeMotor.setNeutralMode(NeutralMode.Brake);

        setConstantTuning();
    }

    public void eject() {
        hatchIntakeMotor.set(RobotMap.HATCH_OUTTAKE_SPEED);
    }

    public void intake() {
        hatchIntakeMotor.set(RobotMap.HATCH_INTAKE_SPEED);
    }

    public void stop() {
        hatchIntakeMotor.set(0.0);
    }

    public void setConstantTuning() {
        SmartDashboard.putNumber("Hatch Intake Speed", RobotMap.HATCH_INTAKE_SPEED);
        SmartDashboard.putNumber("Hatch Outtake Speed", RobotMap.HATCH_OUTTAKE_SPEED);

    }

    public void getConstantTuning() {
        RobotMap.HATCH_INTAKE_SPEED = SmartDashboard.getNumber("Hatch Intake Speed", RobotMap.HATCH_INTAKE_SPEED);
        RobotMap.HATCH_OUTTAKE_SPEED = SmartDashboard.getNumber("Hatch Intake Speed", RobotMap.HATCH_OUTTAKE_SPEED);

    }

    public static HatchIntake getInstance() {
        if (instance == null) {
            instance = new HatchIntake();
        }
        return instance;
    }
}
