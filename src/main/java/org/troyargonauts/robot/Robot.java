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
import org.troyargonauts.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private Command autonomousCommand;

    private static Intake_Nithin intakeNithin;
    private static RobotContainer robotContainer;

    private final SendableChooser<Command> chooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        intakeNithin = new Intake_Nithin();
        robotContainer = new RobotContainer();

        // autonomous chooser on the dashboard.
        SmartDashboard.putData("Autonomous modes", chooser);
        chooser.addOption("Nothing", null);
        chooser.addOption("run", new RunCommand(() -> Robot.getIntakeNithin().setMotor(0.5), Robot.getIntakeNithin()));
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
        SmartDashboard.putNumber("Left Y", RobotContainer.getDriver().getLeftJoystickY());
        SmartDashboard.putNumber("Right X", RobotContainer.getDriver().getRightJoystickX());
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

    public static Intake_Nithin getIntakeNithin() {
        if (intakeNithin == null) {
            intakeNithin = new Intake_Nithin();
        }
        return intakeNithin;
    }
}
