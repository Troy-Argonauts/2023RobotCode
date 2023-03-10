// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.troyargonauts.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import org.troyargonauts.robot.auton.DriveHybrid;
import org.troyargonauts.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private Command autonomousCommand;
    private static RobotContainer robotContainer;
    private static DriveTrain driveTrain;
    private static Arm arm;
    private static Pneumatics pneumatics;

    private final SendableChooser<Command> chooser = new SendableChooser<>();


    private static Elevator elevator;

    private static Turret turret;
//    static Pneumatics pneumatics;

    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        arm = new Arm();
        driveTrain = new DriveTrain();
        elevator = new Elevator();
        turret = new Turret();
//        pneumatics = new Pneumatics();
        robotContainer = new RobotContainer();

        // autonomous chooser on the dashboard.
        driveTrain.resetEncoders();
        SmartDashboard.putData("Autonomous modes", chooser);
        chooser.addOption("Wrist PID", Robot.getArm().wristPid(0));
        chooser.addOption("Arm PID", Robot.getArm().armPID(-30));
        chooser.setDefaultOption("Drive Straight", new RunCommand(() -> Robot.getDrivetrain().cheesyDrive(0.2, 0, 1), Robot.getDrivetrain()).withTimeout(2.5));
        chooser.addOption("Drive Hybrid Score", new DriveHybrid());
        chooser.addOption("Nothing", null);
//        chooser.addOption("Turn PID", getDrivetrain().turnPID(90));


//        pigeon.configFactoryDefault();
//        pigeon.clearStickyFaults();
//        final Pigeon2Configuration pigeonConfig = new Pigeon2Configuration();
//        pigeonConfig.MountPosePitch = 0;
//        pigeonConfig.MountPoseRoll = 0;
//        pigeonConfig.MountPoseYaw = 0;
//        pigeon.configAllSettings(pigeonConfig);
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Left Y", RobotContainer.getDriver().getLeftY());
        SmartDashboard.putNumber("Right X", RobotContainer.getDriver().getRightX());
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}
    
    @Override
    public void autonomousInit()
    {
        autonomousCommand = chooser.getSelected();
        if (autonomousCommand != null)
        {
            autonomousCommand.schedule();
        }
    }
    
    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit()
    {
        if (autonomousCommand != null)
        {
            autonomousCommand.cancel();
        }
    }
    
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}

    public static Arm getArm() {
        if (arm == null) {
            arm = new Arm();
        }
        return arm;
    }

    public static Pneumatics getPneumatics() {
        if (pneumatics == null) {
            pneumatics = new Pneumatics();
        }
        return pneumatics;
    }
    /** 
     * Returns driveTrain object
     * @return DriveTrain object instantiated in Robot class
     */
    public static DriveTrain getDrivetrain() {
        if (driveTrain == null) {
            driveTrain = new DriveTrain();
        }
        return driveTrain;
    }

    public static RobotContainer getRobotContainer() {
        if (robotContainer == null) {
            robotContainer = new RobotContainer();
        }
        return robotContainer;
    }

    public static Elevator getElevator() {
        if (elevator == null) elevator = new Elevator();
        return elevator;

    }
    public static Turret getTurret() {
        if (turret == null){
            turret = new Turret();
        }

        return turret;
    }
}
