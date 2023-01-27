package org.troyargonauts.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.RainbowAnimation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSystem extends SubsystemBase{

    public static int r;
    public static int b;
    public static int g;
    public static boolean cubeNeeded = true;
    public static boolean coneNeeded = false;
    private final CANdle candle;
    public ErrorCode error;
    public final CANdleConfiguration config;

    public LEDSystem() {
        candle = new CANdle(5);
        config = new CANdleConfiguration();
        config.stripType = CANdle.LEDStripType.RGB;
        candle.configAllSettings(config);

    }

    public void ledTestOn() throws InterruptedException {
        for (int i = 0; i < 255; i++) {
            b += 1;
            r -= 1;
            candle.setLEDs(r, g, b);
        }
        error = candle.getLastError();
        System.out.println(error);
    }

    public void ledStandby(int ledLength) {
        if (coneNeeded == false && cubeNeeded == false) {
            config.brightnessScalar = 0.5; // dim the LEDs to half brightness
            candle.configAllSettings(config);
            RainbowAnimation rainbowAnim = new RainbowAnimation(1, 0.5, ledLength);
            candle.animate(rainbowAnim);
        }
    }


    public void purpleCube(){
        if (cubeNeeded == true) {

                candle.setLEDs(70, 0, 106);
                /*--try {
                    wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                candle.setLEDs(255, 255, 255); --*/

            }


        }


    public void yellowCone(){
        if (coneNeeded == true) {

                candle.setLEDs(0, 255, 255);
                /*--try {
                    wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                candle.setLEDs(255, 255, 255); --*/
        }
    }

}

