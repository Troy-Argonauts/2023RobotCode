package org.troyargonauts;

public final class Constants {

    public interface DriveConstants {
        int kFrontRightID = 2;
        int kMiddleRightID = 3;
        int kBackRightID = 4;
        int kFrontLeftID = 5;
        int kMiddleLeftID = 6;
        int kBackLeftID = 7;

        int kPigeonID = 6;

        double kWheelDiameterInches = 6.0;
        double kEncoderNUPerWheelRevolution = 2004.789;
        double kWheelRevolutionDistanceInches = kWheelDiameterInches * Math.PI;
        double kDistanceConvertion = kEncoderNUPerWheelRevolution / kWheelRevolutionDistanceInches;

        double kEncoderGearboxScale = 8.56;

        double kP = 1;
        double kI = 0;
        double kD = 0;
        
        double kTurnP = 1;
        double kTurnI = 0;
        double kTurnD = 0;

        double kDriveTolerance = 1;
        double kTurnToleranceDeg = 1;
    }

}
