package by.reshetnikov.proweather.utils;

import android.content.Context;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.preferences.units.SpeedUnit;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;

/**
 * Created by SacRahl on 6/12/2017.
 */

public final class UnitUtils {

    private static final double TRIPLE_POINT = 273.15;
    private static final double ONE_METER_PER_SECOND_IN_MILES_PER_HOUR_CONSTANT = 2.2369362920544;
    private static final String WHITESPACE = " ";
    private static final char CELSIUS_SIGN = '\u2103';
    private static final char KELVIN_SIGN = '\u2109';
    private static final char FAHRENHEIT_SIGN = '\u212A';

    public static String getTemperatureWithSign(int fahrenheitTemperature, TemperatureUnit temperatureUnit) {
        if (temperatureUnit == TemperatureUnit.CELSIUS)
            return String.valueOf(UnitUtils.kelvinsToCelsius(fahrenheitTemperature)) + WHITESPACE + CELSIUS_SIGN;
        if (temperatureUnit == TemperatureUnit.KELVIN)
            return String.valueOf(UnitUtils.kelvinsToCelsius(fahrenheitTemperature)) + WHITESPACE + KELVIN_SIGN;
        return String.valueOf(fahrenheitTemperature) + WHITESPACE + FAHRENHEIT_SIGN;
    }

    public static int getTemperature(int fahrenheitTemperature, TemperatureUnit temperatureUnit) {
        if (temperatureUnit == TemperatureUnit.CELSIUS)
            return UnitUtils.kelvinsToCelsius(fahrenheitTemperature);
        if (temperatureUnit == TemperatureUnit.KELVIN)
            return UnitUtils.kelvinsToCelsius(fahrenheitTemperature);
        return fahrenheitTemperature;
    }

    public static String getSpeed(double windSpeed, SpeedUnit speedUnit) {
        Context context = ProWeatherApp.getAppContext();
        if (speedUnit == SpeedUnit.METRES_PER_SECOND)
            return String.valueOf(windSpeed) + WHITESPACE + context.getString(R.string.metres_per_second);
        return metesPerSecondToMilesPerHour(windSpeed) + windSpeed + context.getString(R.string.miles_per_second);
    }

    private static int kelvinsToFahrenheits(double kelvins) {
        return (int) (kelvinsToCelsius(kelvins) * 1.8 + 32);
    }

    private static int kelvinsToCelsius(double kelvins) {
        return (int) (kelvins - TRIPLE_POINT);
    }

    private static String metesPerSecondToMilesPerHour(double metesPerSecond) {
        double mph = metesPerSecond * ONE_METER_PER_SECOND_IN_MILES_PER_HOUR_CONSTANT;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(mph);

    }

    public static String getWindDirection(int degrees) {
        Context context = ProWeatherApp.getAppContext();
        if (degrees >= 348.75 && degrees < 11.25)
            return context.getString(R.string.north);

        if (degrees >= 11.25 && degrees < 33.75)
            return context.getString(R.string.north_north_east);

        if (degrees >= 33.75 && degrees < 56.25)
            return context.getString(R.string.north_east);

        if (degrees >= 56.25 && degrees < 78.75)
            return "ENE";

        if (degrees > 78.75 && degrees < 101.25)
            return context.getString(R.string.east);

        if (degrees >= 101.25 && degrees < 123.75)
            return context.getString(R.string.east_south_east);

        if (degrees >= 123.75 && degrees < 146.25)
            return context.getString(R.string.south_east);

        if (degrees >= 146.25 && degrees < 168.75)
            return context.getString(R.string.south_south_east);

        if (degrees >= 168.75 && degrees < 191.25)
            return context.getString(R.string.south);

        if (degrees >= 191.25 && degrees < 213.75)
            return context.getString(R.string.south_south_west);

        if (degrees >= 213.75 && degrees < 236.25)
            return context.getString(R.string.south_west);

        if (degrees >= 236.25 && degrees < 258.75)
            return context.getString(R.string.west_south_west);

        if (degrees >= 258.75 && degrees < 281.25)
            return context.getString(R.string.west);

        if (degrees >= 281.25 && degrees < 303.75)
            return context.getString(R.string.west_north_west);

        if (degrees >= 303.75 && degrees < 326.25)
            return context.getString(R.string.north_west);

        return context.getString(R.string.north_north_west);
    }

    public static char getSign(TemperatureUnit temperatureUnit) {
        if (temperatureUnit == TemperatureUnit.CELSIUS)
            return CELSIUS_SIGN;
        if (temperatureUnit == TemperatureUnit.KELVIN)
            return KELVIN_SIGN;
        return FAHRENHEIT_SIGN;
    }

    public static String getSign(SpeedUnit speedUnit) {
        Context context = ProWeatherApp.getAppContext();
        if (speedUnit == SpeedUnit.MILES_PER_HOUR)
            return context.getString(R.string.metres_per_second);
        return context.getString(R.string.miles_per_second);
    }
}