package by.reshetnikov.proweather.data.appmodels;

import by.reshetnikov.proweather.data.MeasureUnits;
import by.reshetnikov.proweather.data.TemperatureUnits;
import by.reshetnikov.proweather.data.WindDirection;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.Wind;
import by.reshetnikov.proweather.utils.UnitUtils;

/**
 * Created by SacRahl on 6/12/2017.
 */

public class CurrentWeatherAppModel {

    private static final String KELVINS = " \u212A";
    private static final String CELSIUS = " \u2103";
    private static final String FAHRENHEITS = " \u2109";
    private String temperature;
    private String humidity;
    private String wind;
    private String windDirection;

    public CurrentWeatherAppModel(CurrentWeather weather) {

        MeasureUnits measureUnits = MeasureUnits.Meter;
        TemperatureUnits temperatureUnits = TemperatureUnits.Celsius;

        humidity = String.valueOf(weather.main.humidity) + "%";
        windDirection = getWindDirection(weather.wind).name();

        if (MeasureUnits.Meter == measureUnits) {
            wind = String.valueOf(weather.wind.speed);
        }
        if (MeasureUnits.Mile == measureUnits) {
            wind = String.valueOf(weather.wind.speed);
        }
        if (TemperatureUnits.Kelvin == temperatureUnits) {
            temperature = String.valueOf(weather.main.temperature) + KELVINS;
        }
        if (TemperatureUnits.Celsius == temperatureUnits) {
            temperature = String.valueOf(UnitUtils.kelvinsToCelsius(weather.main.temperature)) + CELSIUS;
        }
        if (TemperatureUnits.Fahrenheit == temperatureUnits) {
            temperature = String.valueOf(UnitUtils.kelvinsToCelsius(weather.main.temperature)) + FAHRENHEITS;
        }
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }

    public String getWindDirection() {
        return windDirection;
    }

    private WindDirection getWindDirection(Wind wind) {
        if (wind.degrees >= 348.75 && wind.degrees < 11.25)
            return WindDirection.N;

        if (wind.degrees >= 11.25 && wind.degrees < 33.75)
            return WindDirection.NNE;

        if (wind.degrees >= 33.75 && wind.degrees < 56.25)
            return WindDirection.NE;

        if (wind.degrees >= 56.25 && wind.degrees < 78.75)
            return WindDirection.ENE;

        if (wind.degrees > 78.75 && wind.degrees < 101.25)
            return WindDirection.E;

        if (wind.degrees >= 101.25 && wind.degrees < 123.75)
            return WindDirection.ESE;

        if (wind.degrees >= 123.75 && wind.degrees < 146.25)
            return WindDirection.SE;

        if (wind.degrees >= 146.25 && wind.degrees < 168.75)
            return WindDirection.SSE;

        if (wind.degrees >= 168.75 && wind.degrees < 191.25)
            return WindDirection.S;

        if (wind.degrees >= 191.25 && wind.degrees < 213.75)
            return WindDirection.SSW;

        if (wind.degrees >= 213.75 && wind.degrees < 236.25)
            return WindDirection.SW;

        if (wind.degrees >= 236.25 && wind.degrees < 258.75)
            return WindDirection.WSW;

        if (wind.degrees >= 258.75 && wind.degrees < 281.25)
            return WindDirection.W;

        if (wind.degrees >= 281.25 && wind.degrees < 303.75)
            return WindDirection.WNW;

        if (wind.degrees >= 303.75 && wind.degrees < 326.25)
            return WindDirection.NW;

        return WindDirection.NNW;


    }
}
