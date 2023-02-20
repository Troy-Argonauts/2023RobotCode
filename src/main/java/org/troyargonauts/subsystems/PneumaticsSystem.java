package org.troyargonauts.subsystems;

import org.troyargonauts.Constants.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticsSystem extends SubsystemBase {

    public static DoubleSolenoid alignSolenoid;
    public static DoubleSolenoid grabSolenoid;
    public static DoubleSolenoid rotateSolenoid;
    
    public enum AlignIntakeState {
        ALIGN, UNALIGN;
    }

    public enum GrabIntakeState {
        GRAB, RELEASE;
    }
    public enum RotateIntakeState {
        FLAT, ROTATED;
    }

    public PneumaticsSystem() {
        alignSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kAlignForwardChannel, IntakeConstants.kAlignReverseChannel);
        alignSolenoid.set(DoubleSolenoid.Value.kForward);

        grabSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kGrabForwardChannel, IntakeConstants.kGrabReverseChannel);
        grabSolenoid.set(DoubleSolenoid.Value.kForward);
        
        rotateSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kRotateForwardChannel, IntakeConstants.kRotateReverseChannel);
        rotateSolenoid.set(DoubleSolenoid.Value.kForward);
    }
}