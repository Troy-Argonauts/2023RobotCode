package org.troyargonauts.constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import org.troyargonauts.common.network.SmartBoolean;

import java.nio.file.Path;

public interface Settings {

    Path DEPLOY_DIRECTORY = Filesystem.getDeployDirectory().toPath();

    SmartBoolean DEBUG_MODE = new SmartBoolean("Debug Mode", true);
    SmartBoolean ENABLE_WARNINGS = new SmartBoolean("Enable Warnings", true);

    static void reportWarning(String warning) {
        if (ENABLE_WARNINGS.get()) {
            DriverStation.reportWarning(warning, false);
        }
    }

    interface Arm {

    }

    interface Drivetrain {

    }

    interface Elevator {

    }

    interface Turret {

    }

}
