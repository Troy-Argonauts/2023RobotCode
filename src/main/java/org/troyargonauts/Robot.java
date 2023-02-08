// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.troyargonauts;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.troyargonauts.subsystems.Gearbox;
import org.troyargonauts.subsystems.LEDSystem;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;


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

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    private final Color kYellow = new Color(1, 1, 0);
    private final Color kBlue = new Color(0, 0, 1);

    private final ColorMatch colorMatch = new ColorMatch();

    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        gearbox = new Gearbox();
        led = new LEDSystem();

        robotContainer = new RobotContainer();

        colorMatch.addColorMatch(kYellow);
        colorMatch.addColorMatch(kBlue);
    }

    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();

        /**
         * The method GetColor() returns a normalized color value from the sensor and can be
         * useful if outputting the color to an RGB LED or similar. To
         * read the raw color, use GetRawColor().
         *
         * The color sensor works best when within a few inches from an object in
         * well lit conditions (the built in LED is a big help here!). The farther
         * an object is the more light from the surroundings will bleed into the
         * measurements and make it difficult to accurately determine its color.
         */
        Color detectedColor = colorSensor.getColor();

        String colorString;
        ColorMatchResult match = colorMatch.matchClosestColor(detectedColor);

        if (match.color == kBlue) {
            colorString = "Blue";
        } else if (match.color == kYellow){
            colorString = "Yellow";
        } else {
            colorString = "dle";
        }

        /**
         * The sensor returns a raw IR value of the infrared light detected.
         */
        double IR = colorSensor.getIR();

        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the
         * sensor.
         */
        SmartDashboard.putString("Color", detectedColor.red + ", " + detectedColor.green + ", " + detectedColor.blue);
        SmartDashboard.putString("Yellow Detected", colorString);
//        SmartDashboard.putNumber("Green", detectedColor.green);
//        SmartDashboard.putNumber("Blue", detectedColor.blue);
//        SmartDashboard.putNumber("IR", IR);

        /**
         * In addition to RGB IR values, the color sensor can also return an
         * infrared proximity value. The chip contains an IR led which will emit
         * IR pulses and measure the intensity of the return. When an object is
         * close the value of the proximity will be large (max 2047 with default
         * settings) and will approach zero when the object is far away.
         *
         * Proximity can be used to roughly approximate the distance of an object
         * or provide a threshold for when an object is close enough to provide
         * accurate color values.
         */
//        int proximity = colorSensor.getProximity();
//
//        SmartDashboard.putNumber("Proximity", proximity);
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
}
