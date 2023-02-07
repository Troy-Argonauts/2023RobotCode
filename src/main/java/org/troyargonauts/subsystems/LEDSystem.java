package org.troyargonauts.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.RainbowAnimation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class LEDSystem extends SubsystemBase {
    // The Variables
    public final static int blackR = 184;
    public final static int blackG = 134;
    public final static int blackB = 11;

    public final static int goldR = 51;
    public final static int goldG = 51;
    public final static int goldB = 58;

    public final static int yellowR = 255;
    public final static int yellowG = 255;
    public final static int yellowB = 0;

    public final static int purpleR = 70;
    public final static int purpleG = 0;
    public final static int purpleB = 106;

    public static int red = 255;
    public static int green = 0;
    public static int blue = 0;

    private final CANdle candle;
    public CANdleConfiguration config;


    public LEDSystem() {
        candle = new CANdle(5);
        config = new CANdleConfiguration();
        config.stripType = CANdle.LEDStripType.RGB;
        candle.configAllSettings(config);
    }

    public void ledTestOn(boolean True) {
        if(True) {
            candle.setLEDs(255, 255, 255);
        }
    }

    public void ledStandby(int ledLength, boolean True) {
        if(True) {
            // dim the LEDs to half brightness
            config.brightnessScalar = 0.5;
            candle.configAllSettings(config);
            RainbowAnimation rainbowAnim = new RainbowAnimation(1, 0.5, ledLength);
            candle.animate(rainbowAnim);
        }
    }

    // We could maybe set it up so that if we win, these are the lights that will display
    public void argoColors(boolean win) {
        if(win) {
            candle.setLEDs(goldR, goldG, goldB);
        }
    }
    // Switches color from "Black" (dark gray) to gold

    public void purpleCube(boolean purpleCube) {
        if(purpleCube) {
            candle.setLEDs(purpleR, purpleG, purpleB);
        }
    }

    public void yellowCone(boolean yellowCone) {
        if(yellowCone) {
            candle.setLEDs(yellowR, yellowG, yellowB);
        }
    }

    public void ledOff(boolean off) {
        if(off) {
            candle.setLEDs(0, 0, 0);
        }
    }

    public void losingState(boolean lose) {
        if(lose) {
            candle.setLEDs(255, 17, 4);
        }
    }
}
