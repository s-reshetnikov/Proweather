package by.reshetnikov.proweather.utils;

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
}
