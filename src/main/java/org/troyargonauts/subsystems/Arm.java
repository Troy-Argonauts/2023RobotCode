package org.troyargonauts.subsystems;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.troyargonauts.Constants;
import org.troyargonauts.Robot;

/**
 * Class representing arm. Includes PID control and absolute encoders
 * @author TeoElRey, ASH-will-WIN, SolidityContract
 */
public class Arm extends SubsystemBase {
    private final CANSparkMax elbowMotor;
    private final CANSparkMax manipulatorMotor;
    private final CANSparkMax wristMotor;
    private final AbsoluteEncoder elbowEncoder;
    private final AbsoluteEncoder wristEncoder;
    private final PIDController wristPID, elbowPID;
    private double elbowEncoderValue;
    private double wristEncoderValue;

    /**
     * Here, the motors, absolute encoders, and PID Controller are instantiated.
     */
    public Arm() {
        elbowMotor = new CANSparkMax(Constants.Arm.ARM_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        manipulatorMotor = new CANSparkMax(Constants.Arm.ARM_INTAKE, CANSparkMaxLowLevel.MotorType.kBrushless);
        wristMotor = new CANSparkMax(Constants.Arm.ARM_INTAKE, CANSparkMaxLowLevel.MotorType.kBrushless);

        elbowMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        manipulatorMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        wristMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        elbowEncoder = elbowMotor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
        wristEncoder = wristMotor.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);

        wristPID = new PIDController(Constants.Arm.kWristP, Constants.Arm.kWristI, Constants.Arm.kWristD, Constants.Arm.WRIST_PERIOD);
        elbowPID = new PIDController(Constants.Arm.kElbowP, Constants.Arm.kElbowI, Constants.Arm.kElbowD, Constants.Arm.ELBOW_PERIOD);
    }
    @Override
    public void periodic() {
        elbowEncoderValue = elbowEncoder.getPosition();
        wristEncoderValue = wristEncoder.getPosition();

        SmartDashboard.putNumber("Elbow Encoder", elbowEncoderValue);
        SmartDashboard.putNumber("Wrist Encoder", wristEncoderValue);
    }

    /**
     * Enums are the states of the intake rollers (FORWARD, OFF, BACKWARD).
     */
    public enum IntakeState {
        FORWARD, OFF, BACKWARD
    }

    /**
     * Intake roller state is set here.
     * @param state desired state of intake rollers to a motor speed.
     */
    public void setIntakeState(IntakeState state) {
        switch(state) {
            case FORWARD:
                manipulatorMotor.set(Constants.Arm.FORWARD_INTAKE_SPEED);
                break;
            case OFF:
                manipulatorMotor.set(0);
                break;
            case BACKWARD:
                manipulatorMotor.set(Constants.Arm.REVERSE_INTAKE_SPEED);
                break;
        }
    }

    /**
     * Sets Elbow to set speed given it is within encoder limits.
     * @param speed sets elbow motor to desired speed given that it is within the encoder limits.
     */
    public void setElbowPower(double speed) {
        if ((getElbowPosition() < Constants.Arm.TOP_ELBOW_ENCODER_LIMIT) || (getWristPosition() > Constants.Arm.LOWER_ELBOW_ENCODER_LIMIT)) {
            elbowMotor.set(0);
        }
        else {
            elbowMotor.set(speed);
        }
    }

    /**
     * Sets Wrist to set speed given it is within encoder limits.
     * @param speed sets wrist motor to desired speed given that it is within the encoder limits.
     */
    public void setWristPower(double speed) {
        if ((getWristPosition() < Constants.Arm.TOP_WRIST_ENCODER_LIMIT) || (getWristPosition() > Constants.Arm.LOWER_WRIST_ENCODER_LIMIT)) {
            wristMotor.set(0);
        }
        else {
            wristMotor.set(speed);
        }
    }

    /**
     * Returns elbow position
     * @return returns the position of elbow
     */
    public double getElbowPosition() {
        return elbowEncoderValue;
    }

    /**
     * Returns wrist position
     * @return returns the position of wrist
     */
    public double getWristPosition() {
        return wristEncoderValue;
    }

    /**
     * Using a PID command, elbow will rotate to a setpoint using the PID Controller.
     * @param setpoint the point desired to be reached by the elbow.
     */
    public PIDCommand elbowPid(double setpoint) {
        pid.setSetpoint(setpoint);
        return new PIDCommand(
            elbowPID,
            this::getElbowPosition,
            setpoint,
            this::setElbowPower,
            Robot.getArm()
        );
    }

    /**
     * Using a PID command, wrist will rotate to a setpoint using the PID Controller.
     * @param setpoint the point desired to be reached by the wrist
     */
    public PIDCommand wristPid(double setpoint) {
        pid.setSetpoint(setpoint);
        return new PIDCommand(
            wristPID,
            this::getWristPosition,
            setpoint,
            this::setWristPower,
            Robot.getArm()
        );
    }
}
