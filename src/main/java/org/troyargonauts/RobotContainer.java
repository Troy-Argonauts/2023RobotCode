// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.troyargonauts;

import edu.wpi.first.wpilibj2.command.*;
import org.troyargonauts.libs.ArgoController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    ArgoController driver = new ArgoController(0);
    boolean buttonPressed = true;

    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
    }
    
    
    /** Use this method to define your trigger->command mappings. */
    private void configureBindings()
    {
        Robot.getGearbox().setDefaultCommand(
                new RunCommand(() ->  {
                    Robot.getGearbox().cheesyDrive((driver.getRightJoystickX() * 0.5), driver.getLeftJoystickY(), 0.25);
                }, Robot.getGearbox())
        );

        driver.getAButton().toggleOnTrue(new InstantCommand(() -> Robot.getLEDs().purpleCube(true), Robot.getLEDs()));
        driver.getBButton().toggleOnTrue(new InstantCommand(() -> Robot.getLEDs().yellowCone(true), Robot.getLEDs()));
    }
    
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // TODO: Implement properly
        return null;
    }
}
