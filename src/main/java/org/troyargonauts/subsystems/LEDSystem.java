package org.troyargonauts.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.RainbowAnimation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static java.lang.Thread.sleep;

public class LEDSystem extends SubsystemBase{

    public static int r = 255;
    public static int g = 0;
    public static int b = 0;

    public static boolean cubeNeeded = false;
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

    public void ledTestOn()  {
        /*for (int i = 0; i < 255; i++) {
            b += 1;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(r != 0) {
                r -= 1;
                System.out.println(r);
            }*/
            r = 184;
            g = 134;
            b = 11;
            candle.setLEDs(r, g, b);
        }



    public void ledStandby(int ledLength) {
        if (!coneNeeded && !cubeNeeded) {
            config.brightnessScalar = 0.5; // dim the LEDs to half brightness
            candle.configAllSettings(config);
            RainbowAnimation rainbowAnim = new RainbowAnimation(1, 0.5, ledLength);
            candle.animate(rainbowAnim);
        }
    }

    public void argoColors(int[] black, int[] gold, int loops){
        int blackR = black[0];
        int blackG = black[1];
        int blackB = black[2];

        int goldR = gold[0];
        int goldG = gold[1];
        int goldB = gold[2];

        for (int i = 0; i < loops.length; i++) {
            candle.setLEDs(blackR, blackG, blackB);
            Thread.sleep(2000);
            candle.setLEDs(goldR, goldG, goldB);
            Thread.sleep(2000);
        }
    }


    public void purpleCube(){
        if (cubeNeeded) {

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
        if (coneNeeded) {

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

