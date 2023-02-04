package org.troyargonauts.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gearbox extends SubsystemBase {
    /**
     * using PID, used
     * @author @SolidityContract @sgowda260 @Shreyan-M
     * @param DriveTrain This method lists the PID values and the motors for the drivetrain
     */
    private CANSparkMax frontRight, middleRight, backRight, frontLeft, middleLeft, backLeft;

    public Gearbox() {
        frontRight = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        middleRight = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
        backRight = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);

        frontRight.setInverted(true);
        middleRight.setInverted(true);
        backRight.setInverted(true);

        backRight.follow(frontRight);
        middleRight.follow(frontRight);
    }


    /**
     * Sets motors value based on speed and turn parameters
     * @param speed speed of robot
     * @param turn amount we want to turn
     * @param nerf decreases the max speed and amount we want to turn the robot
     */
    public void cheesyDrive(double turn, double speed, double nerf) {
        frontRight.set((speed + turn) * nerf);
    }
}