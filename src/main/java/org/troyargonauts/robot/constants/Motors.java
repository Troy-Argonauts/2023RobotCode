package org.troyargonauts.robot.constants;

import com.revrobotics.CANSparkMax;

public interface Motors {

    interface Drivetrain {
        int CURRENT_LIMIT_AMPS = 60;
        CANSparkMax.IdleMode IDLE_MODE = CANSparkMax.IdleMode.kBrake;
        Config LEFT = new Config(false, IDLE_MODE, CURRENT_LIMIT_AMPS);
        Config RIGHT = new Config(true, IDLE_MODE, CURRENT_LIMIT_AMPS);
    }

    interface Elevator {
        int CURRENT_LIMIT_AMPS = 35;

    }

    interface Turret {

    }

    interface Arm {

    }

    interface Wrist {

    }
    class Config {
        public final boolean INVERTED;
        public final CANSparkMax.IdleMode IDLE_MODE;
        public final int CURRENT_LIMIT_AMPS;
        public final double OPEN_LOOP_RAMP_RATE;

        public Config(
                boolean inverted,
                CANSparkMax.IdleMode idleMode,
                int currentLimitAmps,
                double openLoopRampRate) {
            this.INVERTED = inverted;
            this.IDLE_MODE = idleMode;
            this.CURRENT_LIMIT_AMPS = currentLimitAmps;
            this.OPEN_LOOP_RAMP_RATE = openLoopRampRate;
        }

        public Config(boolean inverted, CANSparkMax.IdleMode idleMode, int currentLimitAmps) {
            this(inverted, idleMode, currentLimitAmps, 0.0);
        }

        public Config(boolean inverted, CANSparkMax.IdleMode idleMode) {
            this(inverted, idleMode, 80);
        }

        public void configure(CANSparkMax motor) {
            motor.setInverted(INVERTED);
            motor.setIdleMode(IDLE_MODE);
            motor.setSmartCurrentLimit(CURRENT_LIMIT_AMPS);
            motor.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE);
            motor.burnFlash();
        }
    }
}
