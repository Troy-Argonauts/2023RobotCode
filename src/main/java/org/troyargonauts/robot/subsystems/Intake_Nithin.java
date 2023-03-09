package org.troyargonauts.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.troyargonauts.robot.Constants;

public class Intake_Nithin extends SubsystemBase {
    TalonFX motor;
    public Intake_Nithin() {
        motor = new TalonFX(9);
        motor.setNeutralMode(NeutralMode.Brake);
        motor.configOpenloopRamp(0);
        motor.configClosedloopRamp(0);
    }

    public void setMotor(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }
}

