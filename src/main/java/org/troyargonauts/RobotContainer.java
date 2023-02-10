// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.troyargonauts;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    public ArgoController argoController;
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
        argoController = new ArgoController(0, 0);
    }
    
    
    /** Use this method to define your trigger->command mappings. */
    private void configureBindings() {
        Robot.getDrivetrain().setDefaultCommand(
            new RunCommand(
                () -> {
                   Robot.getDrivetrain().tankDrive(argoController.getLeftJoystickY(), argoController.getRightJoystickY());
                }
            )
        );
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
