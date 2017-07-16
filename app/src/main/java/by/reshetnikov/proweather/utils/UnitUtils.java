package by.reshetnikov.proweather.utils;

import by.reshetnikov.proweather.data.preferences.units.WindDirection;

/**
 * Created by SacRahl on 6/12/2017.
 */

public final class UnitUtils {

    private static final double TRIPLE_POINT = 273.15;
    private static final double ONE_METER_PER_SECOND_IN_MILES_PER_HOUR = 2.2369362920544;

    public static int kelvinsToFahrenheits(double kelvins) {
        return (int) (kelvinsToCelsius(kelvins) * 1.8 + 32);
    }

    public static int kelvinsToCelsius(double kelvins) {
        return (int) (kelvins - TRIPLE_POINT);
    }

    public static double metesPerSecondToMilesPerHour(double metesPerSecond) {
        return metesPerSecond * ONE_METER_PER_SECOND_IN_MILES_PER_HOUR;
    }

    public WindDirection getWindDirection(double degrees) {
        if (degrees >= 348.75 && degrees < 11.25)
            return WindDirection.N;

        if (degrees >= 11.25 && degrees < 33.75)
            return WindDirection.NNE;

        if (degrees >= 33.75 && degrees < 56.25)
            return WindDirection.NE;

        if (degrees >= 56.25 && degrees < 78.75)
            return WindDirection.ENE;

        if (degrees > 78.75 && degrees < 101.25)
            return WindDirection.E;

        if (degrees >= 101.25 && degrees < 123.75)
            return WindDirection.ESE;

        if (degrees >= 123.75 && degrees < 146.25)
            return WindDirection.SE;

        if (degrees >= 146.25 && degrees < 168.75)
            return WindDirection.SSE;

        if (degrees >= 168.75 && degrees < 191.25)
            return WindDirection.S;

        if (degrees >= 191.25 && degrees < 213.75)
            return WindDirection.SSW;

        if (degrees >= 213.75 && degrees < 236.25)
            return WindDirection.SW;

        if (degrees >= 236.25 && degrees < 258.75)
            return WindDirection.WSW;

        if (degrees >= 258.75 && degrees < 281.25)
            return WindDirection.W;

        if (degrees >= 281.25 && degrees < 303.75)
            return WindDirection.WNW;

        if (degrees >= 303.75 && degrees < 326.25)
            return WindDirection.NW;

        return WindDirection.NNW;
    }
}
