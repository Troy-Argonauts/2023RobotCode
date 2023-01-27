package org.troyargonauts.libs;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Wrapper class for FRC joysticks.
 *
 * @author Shaun Dantas
 */

public class ArgoController extends Joystick {

    public ArgoController(int port) {
        super(port);
    }

    private JoystickButton A = new JoystickButton(this, 1);
    private JoystickButton B = new JoystickButton(this, 2);
    private JoystickButton X = new JoystickButton(this, 3);
    private JoystickButton Y = new JoystickButton(this, 4);
    private JoystickButton LB = new JoystickButton(this, 5);
    private JoystickButton RB = new JoystickButton(this, 6);
    private JoystickButton SELECT = new JoystickButton(this, 7);
    private JoystickButton START = new JoystickButton(this, 8);
    private JoystickButton LS = new JoystickButton(this, 9);
    private JoystickButton RS = new JoystickButton(this, 10);

    public JoystickButton getAButton() {
        return A;
    }

    public JoystickButton getBButton() {
        return B;
    }

    public JoystickButton getXButton() {
        return X;
    }

    public JoystickButton getYButton() {
        return Y;
    }

    public JoystickButton getLBButton() {
        return LB;
    }

    public JoystickButton getRBButton() {
        return RB;
    }

    public JoystickButton getSELECTButton() {
        return SELECT;
    }

    public JoystickButton getSTARTButton() {
        return START;
    }

    public JoystickButton getLSButton() {
        return LS;
    }

    public JoystickButton getRSButton() {
        return RS;
    }

    public double getLeftJoystickY() {
        if (Math.abs(getY()) < 0.05) {
            return 0;
        }
        return getY();
    }

    public double getRightJoystickX() {
        if (Math.abs(getRawAxis(4)) < 0.05) {
            return 0;
        }
        return getRawAxis(4);

    }

    public double getRightJoystickY() {
        return getRawAxis(5);
    }

    public double getLeftTrigger() {
        return getRawAxis(2);
    }

    public double getRightTrigger() {
        return getRawAxis(3);
    }

    public double getTriggers() {
        return getRightTrigger() - getLeftTrigger();
    }
}