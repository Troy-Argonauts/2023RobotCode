package org.troyargonauts;

public final class Constants {

    public interface DriveConstants {
        final int kFrontRightID = 5;
        final int kMiddleRightID = 6;
        final int kBackRightID = 7;
        final int kFrontLeftID = 2;
        final int kMiddleLeftID = 3;
        final int kBackLeftID = 4;

        final int kPigeonID = 6;

        final double kWheelDiameterInches = 6.0;
        final double kEncoderNUPerWheelRevolution = 2004.789;
        final double kWheelRevolutionDistanceInches = kWheelDiameterInches * Math.PI;
        final double kDistanceConvertion = kEncoderNUPerWheelRevolution / kWheelRevolutionDistanceInches;

        final double kEncoderGearboxScale = 8.56;

        final double kP = 1;
        final double kI = 0;
        final double kD = 0;
        
        final double kTurnP = 1;
        final double kTurnI = 0;
        final double kTurnD = 0;

        final double kDriveTolerance = 1;
        final double kTurnToleranceDeg = 1;
    }

}
