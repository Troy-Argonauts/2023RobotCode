package org.troyargonauts.robot.subsystems;

import com.ctre.phoenix.sensors.Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.troyargonauts.common.math.Angle;
import org.troyargonauts.common.util.motorcontrol.LazyCANSparkMax;
import org.troyargonauts.robot.Constants;
import org.troyargonauts.robot.Robot;
import org.troyargonauts.robot.constants.Motors;

/**
 * Drivetrain allows control of the robot's drivetrain in cheesy drive and tank drive. 
 * Includes a distance PID and a turn PID using the Pigeon 2 from CTRE.
 * @author @SolidityContract @sgowda260 @Shreyan-M
 */
public class DriveTrain extends SubsystemBase {
    private final DifferentialDrive drivetrain;
    private final DifferentialDriveOdometry odometry;
    private final LazyCANSparkMax[] leftMotors, rightMotors;
    private final LazyCANSparkMax masterRightMotor, masterLeftMotor, middleRightMotor, backRightMotor, middleLeftMotor, backLeftMotor;

    private Pigeon2 pigeon;

    private final PIDController drivePID, turnPID, autoBalancePID;

    public double frontRightEncoderValue, middleRightEncoderValue, backRightEncoderValue, frontLeftEncoderValue, middleLeftEncoderValue, backLeftEncoderValue;
    public double gyroValue;

    /**
     * Constructor for the robot's Drivetrain. Instantiates motor controllers, changes encoder conversion factors and instantiates PID controllers.
     * Motor controllers on the right side are reversed and set to follow other motor controllers.
     */

    @SuppressWarnings("resource")
    public DriveTrain() {
        masterLeftMotor = new LazyCANSparkMax(Constants.DriveTrain.FRONT_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        middleLeftMotor = new LazyCANSparkMax(Constants.DriveTrain.MIDDLE_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        backLeftMotor = new LazyCANSparkMax(Constants.DriveTrain.BACK_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);

        masterRightMotor = new LazyCANSparkMax(Constants.DriveTrain.FRONT_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
        middleRightMotor = new LazyCANSparkMax(Constants.DriveTrain.MIDDLE_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
        backRightMotor = new LazyCANSparkMax(Constants.DriveTrain.BACK_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);

        rightMotors = new LazyCANSparkMax[] {
                masterRightMotor,
                middleRightMotor,
                backRightMotor
        };

        leftMotors = new LazyCANSparkMax[] {
                masterLeftMotor,
                middleLeftMotor,
                backLeftMotor
        };

        restoreFactoryDefaults();

        middleLeftMotor.follow(masterLeftMotor);
        backLeftMotor.follow(masterLeftMotor);
        middleRightMotor.follow(masterRightMotor);
        backRightMotor.follow(masterRightMotor);

        drivetrain = new DifferentialDrive(new MotorControllerGroup(leftMotors), new MotorControllerGroup(rightMotors));

        setMotorConfig(Motors.Drivetrain.LEFT, Motors.Drivetrain.RIGHT);

        pigeon = new Pigeon2(Constants.DriveTrain.PIGEON);

        odometry = new DifferentialDriveOdometry(getRotation2d(), 0, 0);

        drivePID = new PIDController(Constants.DriveTrain.kDriveP, Constants.DriveTrain.kDriveI, Constants.DriveTrain.kDriveD);
        turnPID = new PIDController(Constants.DriveTrain.kTurnP, Constants.DriveTrain.kTurnI, Constants.DriveTrain.kTurnD);
        autoBalancePID = new PIDController(Constants.DriveTrain.kBalanceP, Constants.DriveTrain.kBalanceI, Constants.DriveTrain.kBalanceP);

        drivePID.setTolerance(Constants.DriveTrain.kDriveTolerance);
        turnPID.setTolerance(Constants.DriveTrain.kTurnToleranceDeg);
        autoBalancePID.setTolerance(Constants.DriveTrain.kBalanceToleranceDeg);

        turnPID.enableContinuousInput(-180, 180);

        resetEncoders();
        burnFlash();
    }

    @Override
    public void periodic() {
        frontRightEncoderValue = masterRightMotor.getEncoder().getPosition();
        middleRightEncoderValue = middleRightMotor.getEncoder().getPosition();
        backRightEncoderValue = backRightMotor.getEncoder().getPosition();
        frontLeftEncoderValue = masterLeftMotor.getEncoder().getPosition();
        middleLeftEncoderValue = middleLeftMotor.getEncoder().getPosition();
        backLeftEncoderValue = backLeftMotor.getEncoder().getPosition();

        SmartDashboard.putNumber("frontRightEncoderValue", frontRightEncoderValue);
        SmartDashboard.putNumber("middleRightEncoderValue", middleRightEncoderValue);
        SmartDashboard.putNumber("backRightEncoderValue", backRightEncoderValue);
        SmartDashboard.putNumber("frontLeftEncoderValue", frontLeftEncoderValue);
        SmartDashboard.putNumber("middleLeftEncoderValue", middleLeftEncoderValue);
        SmartDashboard.putNumber("backLeftEncoderValue", backLeftEncoderValue);

        gyroValue = pigeon.getYaw();
    }


    
    /** 
     * Sets motors value based on speed and turn parameters. 
     * Robots speed and turn will be controlled by different joysticks.
     * Allows robot to move in specified direction with more control.
     * @param speed speed of robot.
     * @param turn amount we want to turn.
     * @param nerf decreases the max speed and amount we want to turn the robot.
     */
    public void cheesyDrive(double speed, double turn, double nerf) {
        masterRightMotor.set(((speed - turn) + Constants.DriveTrain.RIGHT_CORRECTION) * nerf);
        masterLeftMotor.set((speed + turn) * nerf);
    }

    /** 
     * Sets left and right motor values based on left and right parameters. 
     * Robots left and right side will be controlled by different joysticks.
     * Used for mainly testing and troubleshooting.
     * @param left speed of the left side of the robot.
     * @param right speed of the right side of the robot.
     * @param nerf decreases the max speed and amount we want to turn the robot.
     */
    public void tankDrive(double left, double right, double nerf) {
        masterRightMotor.set((right + Constants.DriveTrain.RIGHT_CORRECTION) * nerf);
        masterLeftMotor.set(left * nerf);
    }

    
    /** 
     * Returns encoder position based on the average value from the frontLeft and frontRight motor controller encoders.
     * @return encoder position based on encoder values.
     */
    public double getPosition() {
        return (getLeftPosition() + getRightPosition()) / 2;
    }

    /** 
     * Returns encoder position based on the value from all the left motor controllers.
     * @return encoder position based on frontLeft motor controller encoder.
     */
    public double getLeftPosition() {
        return (frontLeftEncoderValue + middleLeftEncoderValue + backLeftEncoderValue) / 3;
    }

    /** 
     * Returns encoder position based on the value from all the right motor controllers.
     * @return encoder position based on frontRight motor controller encoder.
     */
    public double getRightPosition() {
        return (frontRightEncoderValue + middleRightEncoderValue + backRightEncoderValue) / 3;
    }

    /**
     * Resets the encoders to a position of 0
     */

    public void resetEncoders() {
        for (CANSparkMax motor : leftMotors) {
            motor.getEncoder().setPosition(0);
        }

        for (CANSparkMax motor : rightMotors) {
            motor.getEncoder().setPosition(0);
        }
    }

    /**
     * Resets the pigeon to a yaw of 0
     */
    public void resetAngle() {
        pigeon.setYaw(0);
    }
    
    /** 
     * Returns angles between -180 and 180 degrees from pigeon
     * @return angle of robot
     */
    public double getRawGyroAngle() {
        double output = gyroValue % 360;
        while (Math.abs(output) > 180) {
            if (output < 0) {
                output += 360;
            } else {
                output -= 360;
            }
        }
        return output;
    }

    public Angle getAngle() {
        return Angle.fromDegrees(getRawGyroAngle());
    }

    public Rotation2d getRotation2d() {
        return getAngle().getRotation2d();
    }

    /** 
     * Uses PIDController to turn the robot a certain angle based on the pigeons yaw
     * @param angle the angle we want the robot to be at
     * @return PIDCommand that turns robot to target angle
     */
    public PIDCommand turnPID(double angle) {
        return new PIDCommand(
            turnPID,
            () -> getRawGyroAngle(),
            angle,
            output -> cheesyDrive(0, output, 1),
            Robot.getDrivetrain()
        );
    }

    /** 
     * Uses PIDController to drive the robot a certain distance based on the average of the left and right encoder values
     * @param setpoint the setpoint in inches we want the robot to drive to.
     * @return PIDCommand that turns robot to target angle
     */
    public PIDCommand drivePID(double setpoint) {
        return new PIDCommand(
            drivePID,
            () -> getPosition(),
            setpoint,
            output -> cheesyDrive(output, 0, 1),
            Robot.getDrivetrain()
        );
    }

    /** 
     * Causes the robot to brake.
     * Resets encoders to 0.
     */
    public void brakeMode() {
        resetEncoders();
        drivePID(0);
    }

    private void setMotorConfig(Motors.Config left, Motors.Config right) {
        for (CANSparkMax motor : leftMotors) {
            left.configure(motor);
        }

        for (CANSparkMax motor : rightMotors) {
            right.configure(motor);
        }
    }

    private void restoreFactoryDefaults() {
        for (CANSparkMax motor : leftMotors) {
            motor.restoreFactoryDefaults();
        }

        for (CANSparkMax motor : rightMotors) {
            motor.restoreFactoryDefaults();
        }
    }

    private void burnFlash() {
        for (CANSparkMax motor : leftMotors) {
            motor.burnFlash();
        }

        for (CANSparkMax motor : rightMotors) {
            motor.burnFlash();
        }
    }






    /** 
     * Uses PIDController to balance the robot on the charging station based on the angular offset determined by the gyro.
     * @return PIDCommand that balances robot.
     */
    public PIDCommand autoBalance() {
        return new PIDCommand(
            autoBalancePID,
            () -> pigeon.getPitch(),
            0,
            output -> cheesyDrive(output, 0, 0.2),
            Robot.getDrivetrain()
        );
    }

}
