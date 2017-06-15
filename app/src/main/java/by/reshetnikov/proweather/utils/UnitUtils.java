package by.reshetnikov.proweather.utils;

/**
 * Created by SacRahl on 6/12/2017.
 */

public final class UnitUtils {

    private static final double TRIPLE_POINT = 273.15;

    public static int fahrenheitsToCelsius(double fahrenheits) {
        return (int) ((fahrenheits - 32) / 1.8);
    }

    public static int celsiusToFahrenheits(double celsius) {
        return (int) (celsius * 1.8 + 32);
    }

    public static int kelvinsToCelsius(double kelvins) {
        return (int) (kelvins - TRIPLE_POINT);
    }

    public static int celsiusToKelvin(double celsius) {
        return (int) (celsius + TRIPLE_POINT);
    }

}
