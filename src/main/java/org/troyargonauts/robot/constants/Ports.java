package org.troyargonauts.robot.constants;

public interface Ports {

    interface Gamepad {
        int DRIVER = 0;
        int OPERATOR = 1;
    }

    interface Arm {
        int ELBOW = 9;
        int MANIPULATOR = 13;
        int WRIST = 8;
    }

    interface Drivetrain {
        int FRONT_RIGHT = 2;
        int MIDDLE_RIGHT = 3;
        int BACK_RIGHT = 4;
        int FRONT_LEFT = 5;
        int MIDDLE_LEFT = 6;
        int BACK_LEFT = 7;
        int PIGEON = 25;
    }

    interface Elevator {
        int LEFT = 11;
        int RIGHT = 12;
    }

    interface Turret {
        int MOTOR = 10;
    }

}
