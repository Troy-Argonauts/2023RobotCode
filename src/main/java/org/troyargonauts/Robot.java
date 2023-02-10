// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.troyargonauts;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import sensors.ColorSensor;

import org.troyargonauts.subsystems.Gearbox;
import org.troyargonauts.subsystems.LEDSystem;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private Command autonomousCommand;
    
    private RobotContainer robotContainer;
    private static Gearbox gearbox;
    private static LEDSystem led;
    private static ColorSensor colorSensor;

    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        gearbox = new Gearbox();
        led = new LEDSystem();
        colorSensor = new ColorSensor();

        robotContainer = new RobotContainer();

        ColorSensor.colorMatch.addColorMatch(ColorSensor.kYellow);
        ColorSensor.colorMatch.addColorMatch(ColorSensor.kPurple);
        ColorSensor.colorMatch.addColorMatch(ColorSensor.kMiddle);
    }

    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();

        if (getColorSensor().getColor().equals("Purple")) {
            getLEDs().purpleCube(true);
        } else if (getColorSensor().getColor().equals("Yellow")) {
            getLEDs().yellowCone(true);
        } else {
            getLEDs().ledOff(true);
        }
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}
    
    @Override
    public void autonomousInit()
    {
        autonomousCommand = robotContainer.getAutonomousCommand();
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
    public void teleopPeriodic() {}

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

    public static Gearbox getGearbox() {
        if (gearbox == null) {
            gearbox = new Gearbox();
        }
        return gearbox;
    }

    public static LEDSystem getLEDs(){
        if(led == null){
            led = new LEDSystem();
        }
        return led;
    }

    public static ColorSensor getColorSensor() {
        if (colorSensor == null) {
            colorSensor = new ColorSensor();
        }
        return colorSensor;
    }
}
