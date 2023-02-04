package org.troyargonauts.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.RainbowAnimation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class LEDSystem extends SubsystemBase {

    public final static int blackR = 184;
    public final static int blackG = 134;
    public final static int blackB = 11;
    public static boolean blackLoop = true;

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

    public static boolean cubeNeeded = false;
    public static boolean coneNeeded = false;

    public static boolean lose = false;

    private final CANdle candle;
    public CANdleConfiguration config;

    public LEDSystem() {

        candle = new CANdle(5);
        config = new CANdleConfiguration();
        config.stripType = CANdle.LEDStripType.RGB;
        candle.configAllSettings(config);

    }

    public void ledTestOn() {
        blue+=1;
        for (int i = 0; i < 255; i++) {
              try {
                wait(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (red != 0) {
                red -= 1;
                System.out.println(red);
            }
            red = 184;
            green = 134;
            blue = 11;
            candle.setLEDs(red, green, blue);
        }
    }


        public void ledStandby ( int ledLength){
            if (!coneNeeded && !cubeNeeded) {
                // dim the LEDs to half brightness
                config.brightnessScalar = 0.5;
                candle.configAllSettings(config);

                RainbowAnimation rainbowAnim = new RainbowAnimation(1, 0.5, ledLength);
                candle.animate(rainbowAnim);
            }
        }

        // We could maybe set it up so that if we win, these are the lights that will display
        public void argoColors ( int loops){
            for (int j = 0; j < loops; j++) {
                if (blackLoop) {
                    for (int i = 0; i < 255; i++) {
                        if (red != blackR) {
                            red += 1;
                        }
                        if (green != blackG) {
                            green += 1;
                        }
                        if (blue != blackB) {
                            blue += 1;
                        }
                        if (red == blackR && blue == blackB && green == blackG) {
                            try {
                                wait(2000);
                            } catch (InterruptedException ignored) {

                            }
                            blackLoop = false;
                        }


                        System.out.println(red);
                        System.out.println(green);
                        System.out.println(blue);

                        try {
                            wait(100);
                        } catch (InterruptedException ignored) {

                        }

                        candle.setLEDs(red, green, blue);
                    }
                }

                // Switches color from "Black" (dark gray) to gold
                else {
                    for (int i = 0; i < 255; i++) {
                        if (red != goldR) {
                            red += 1;
                        }
                        if (green != goldG) {
                            green += 1;
                        }
                        if (blue != goldB) {
                            blue += 1;
                        }
                        if (red == goldR && blue == goldB && green == goldG) {
                            try {
                                wait(2000);
                            } catch (InterruptedException ignored) {

                            }
                            blackLoop = true;
                        }


                        System.out.println(red);
                        System.out.println(green);
                        System.out.println(blue);

                        try {
                            wait(100);
                        } catch (InterruptedException ignored) {

                        }

                        candle.setLEDs(red, green, blue);
                    }
                }
            }
        }


        public void purpleCube () {
            if (cubeNeeded) {

                candle.setLEDs(70, 0, 106);
                try {
                    wait(650);
                } catch (InterruptedException ignored) {

                }
                candle.setLEDs(0, 0, 0);

            }


        }


        public void yellowCone () {
            if (coneNeeded) {

                candle.setLEDs(0, 255, 255);

                try {
                    wait(650);

                } catch (InterruptedException ignored) {

                }
                candle.setLEDs(0, 0, 0);
            }
        }

        public void losingState () {
            if(lose) {

                candle.setLEDs(255, 0,0);

                        try{
                            wait(650);
                        } catch (InterruptedException ignored) {

                        }
                        candle.setLEDs(255, 17, 4);
            }
        }


}
