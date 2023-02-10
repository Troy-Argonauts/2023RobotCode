package sensors;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase {
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    public static Color kYellow = new Color(0.32, 0.52, 0.14);
    public static Color kPurple = new Color(0.18, 0.31, 0.51);
    public static Color kMiddle = new Color(0.3, 0.5, 0.24);

    public static ColorMatch colorMatch = new ColorMatch();

    Color detectedColor = colorSensor.getColor();

    ColorMatchResult match = colorMatch.matchClosestColor(detectedColor);

    public String getColor() {
        if (match.color == kPurple) {
            return "Purple";
        } else if (match.color == kYellow){
            return "Yellow";
        } else if (match.color == kMiddle){
            return "Nothing";
        } else {
            return "idle";
        }
    }

    @Override
    public void periodic() {
        SmartDashboard.putString("Color", detectedColor.red + ", " + detectedColor.green + ", " + detectedColor.blue);
        SmartDashboard.putString("Color Detected", getColor());
    }

}
