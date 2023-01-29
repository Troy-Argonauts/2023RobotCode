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

    public static int r = 255;
    public static int g = 0;
    public static int b = 0;

    public static boolean cubeNeeded = false;
    public static boolean coneNeeded = false;

    private final CANdle candle;
    public CANdleConfiguration config;

    public LEDSystem() {

        candle = new CANdle(5);
        config = new CANdleConfiguration();
        config.stripType = CANdle.LEDStripType.RGB;
        candle.configAllSettings(config);

    }

    public void ledTestOn() {

        for (int i = 0; i < 255; i++) {
            b += 1;
            try {
                wait(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (r != 0) {
                r -= 1;
                System.out.println(r);
            }
            r = 184;
            g = 134;
            b = 11;
            candle.setLEDs(r, g, b);
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
                        if (r != blackR) {
                            r += 1;
                        }
                        if (g != blackG) {
                            g += 1;
                        }
                        if (b != blackB) {
                            b += 1;
                        }
                        if (r == blackR && b == blackB && g == blackG) {
                            try {
                                wait(2000);
                            } catch (InterruptedException ignored) {

                            }
                            blackLoop = false;
                        }


                        System.out.println(r);
                        System.out.println(g);
                        System.out.println(b);

                        try {
                            wait(100);
                        } catch (InterruptedException ignored) {

                        }

                        candle.setLEDs(r, g, b);
                    }
                }

                // Switches color from "Black" (dark gray) to gold
                else {
                    for (int i = 0; i < 255; i++) {
                        if (r != goldR) {
                            r += 1;
                        }
                        if (g != goldG) {
                            g += 1;
                        }
                        if (b != goldB) {
                            b += 1;
                        }
                        if (r == goldR && b == goldB && g == goldG) {
                            try {
                                wait(2000);
                            } catch (InterruptedException ignored) {

                            }
                            blackLoop = true;
                        }


                        System.out.println(r);
                        System.out.println(g);
                        System.out.println(b);

                        try {
                            wait(100);
                        } catch (InterruptedException ignored) {

                        }

                        candle.setLEDs(r, g, b);
                    }
                }
            }
        }


        public void purpleCube () {
            if (cubeNeeded) {

                candle.setLEDs(70, 0, 106);
                try {
                    wait(500);
                } catch (InterruptedException ignored) {

                }
                candle.setLEDs(255, 255, 255);

            }


        }


        public void yellowCone () {
            if (coneNeeded) {

                candle.setLEDs(0, 255, 255);

                try {
                    wait(500);

                } catch (InterruptedException ignored) {

                }
                candle.setLEDs(255, 255, 255);
            }
        }


}
