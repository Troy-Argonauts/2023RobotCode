package org.troyargonauts.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.sensors.Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.troyargonauts.Robot;
import org.troyargonauts.Constants.DriveConstants;
/**
 * using PID and stating our speed, turn, and nerf we made a code to run our 8 wheel tank drivetrain with 2 motors
 * @author @SolidityContract @sgowda260 @Shreyan-M
 */
public class DriveTrain extends SubsystemBase {

    private CANSparkMax frontRight, middleRight, backRight, frontLeft, middleLeft, backLeft;

    /**
     * Creates a new drivetrain object for the code and states the motors needed for the drivetrain
     */

    public DriveTrain() {
//        frontRight = new CANSparkMax(DriveConstants.kFrontRightID, CANSparkMaxLowLevel.MotorType.kBrushless);
//        middleRight = new CANSparkMax(DriveConstants.kMiddleRightID, CANSparkMaxLowLevel.MotorType.kBrushless);
//        backRight = new CANSparkMax(DriveConstants.kBackRightID, CANSparkMaxLowLevel.MotorType.kBrushless);
//        frontLeft = new CANSparkMax(DriveConstants.kFrontLeftID, CANSparkMaxLowLevel.MotorType.kBrushless);
//        middleLeft = new CANSparkMax(DriveConstants.kMiddleLeftID, CANSparkMaxLowLevel.MotorType.kBrushless);
//        backLeft = new CANSparkMax(DriveConstants.kBackLeftID, CANSparkMaxLowLevel.MotorType.kBrushless);
//
//        frontLeft.setInverted(true);
//        middleLeft.setInverted(true);
//        backLeft.setInverted(true);
//
//        backRight.follow(frontRight);
//        middleRight.follow(frontRight);
//
//        backLeft.follow(frontLeft);
//        middleLeft.follow(frontLeft);
    }

    
    /** 
     * Sets motors value based on speed and turn parameters
     * @param speed speed of robot
     * @param turn amount we want to turn
     */
    public void cheesyDrive(double speed, double turn, boolean square, double nerf) {
        if (square) {
            frontRight.set((speed + turn) * (speed + turn));
            frontLeft.set((speed - turn) * (speed - turn));
        } else {
            frontRight.set((speed + turn) * nerf);
            frontLeft.set((speed - turn) * nerf);
        }
    }

    public void tankDrive(double left, double right, boolean square, double nerf) {
        if (square) {
            frontRight.set(-right * right);
            frontLeft.set(-left * left);
        } else {
            frontRight.set(-right * nerf);
            frontLeft.set(-left * nerf);
        }
    }

    
    /** 
     * Returns encoder position based on encoder values
     * @return encoder position based on encoder values
     */
    public double getPosition() {
        return (frontRight.getEncoder().getPosition() + frontLeft.getEncoder().getPosition()) / (2 * DriveConstants.kEncoderGearboxScale);
    }

    /**
     * Resets the encoders
     * @return encoder value to 0
     */

    public void resetEncoders() {
        frontRight.getEncoder().setPosition(0);
        middleRight.getEncoder().setPosition(0);
        backRight.getEncoder().setPosition(0);
        frontLeft.getEncoder().setPosition(0);
        middleLeft.getEncoder().setPosition(0);
        backLeft.getEncoder().setPosition(0);
    }

    public void testDrive(double left, double right) {
        DriverStation.reportWarning("Entered Test Drive", true);
        frontRight.set(right);
        frontLeft.set(left);
        DriverStation.reportWarning("Set Motor Powers", true);
    }

    public void testAuton() {
        frontLeft.set(0.3);
        frontRight.set(0.3);
    }
}
