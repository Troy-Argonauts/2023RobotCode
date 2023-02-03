package org.troyargonauts.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.troyargonauts.Constants.DriveConstants;

public class DriveTrain extends SubsystemBase {
    private CANSparkMax frontRight, middleRight, backRight, frontLeft, middleLeft, backLeft;

    public DriveTrain() {
        frontRight = new CANSparkMax(DriveConstants.kFrontRightID, MotorType.kBrushless);
        middleRight = new CANSparkMax(DriveConstants.kMiddleRightID, MotorType.kBrushless);
        backRight = new CANSparkMax(DriveConstants.kBackRightID, MotorType.kBrushless);
        frontLeft = new CANSparkMax(DriveConstants.kFrontLeftID, MotorType.kBrushless);
        middleLeft = new CANSparkMax(DriveConstants.kMiddleLeftID, MotorType.kBrushless);
        backLeft = new CANSparkMax(DriveConstants.kBackLeftID, MotorType.kBrushless);

        frontRight.setInverted(true);
        middleRight.setInverted(true);
        backRight.setInverted(true);

        backRight.follow(frontRight);
        middleRight.follow(frontRight);

        backLeft.follow(frontLeft);
        middleLeft.follow(frontRight);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Front Right Position", frontRight.getEncoder().getPosition());
        SmartDashboard.putNumber("Front Left Position", frontLeft.getEncoder().getPosition());
    }

    public void tankDrive(double rightSpeed, double leftSpeed, double nerf) {
        frontRight.set(rightSpeed * nerf);
        frontLeft.set(leftSpeed * nerf);
    }
}
