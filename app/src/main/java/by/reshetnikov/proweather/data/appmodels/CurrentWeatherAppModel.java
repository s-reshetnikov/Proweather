package by.reshetnikov.proweather.data.appmodels;

import by.reshetnikov.proweather.data.TemperatureUnit;
import by.reshetnikov.proweather.data.WindDirection;
import by.reshetnikov.proweather.data.WindSpeedUnit;
import by.reshetnikov.proweather.utils.UnitUtils;


public class CurrentWeatherAppModel {

    private static final String KELVINS = " \u212A";
    private static final String CELSIUS = " \u2103";
    private static final String FAHRENHEITS = " \u2109";
    private double temperature;
    private int humidity;
    private double windSpeed;
    private double windDegrees;
    private double rain;
    private double snow;
    private int pressure;
    private int weatherConditionId;
    private String weatherDescription;
    private String iconCode;
    private UnitsAppModel units;

    public CurrentWeatherAppModel() {
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getWeatherConditionId() {
        return weatherConditionId;
    }

    public void setWeatherConditionId(int weatherConditionId) {
        this.weatherConditionId = weatherConditionId;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDegrees(double windDegrees) {
        this.windDegrees = windDegrees;
    }

    public void applyUnits(UnitsAppModel units) {
        this.units = units;
    }

    public String getTemperatureText() {
        if (units == null)
            return String.valueOf(UnitUtils.kelvinsToCelsius(temperature) + CELSIUS);

        if (units.getTemperature() == TemperatureUnit.KELVIN) {
            return String.valueOf(temperature) + KELVINS;
        }
        if (units.getTemperature() == TemperatureUnit.FAHRENHEIT) {
            return String.valueOf(UnitUtils.kelvinsToFahrenheits(temperature)) + FAHRENHEITS;
        }
        return String.valueOf(UnitUtils.kelvinsToCelsius(temperature) + CELSIUS);
    }

    public String getWindSpeedText() {
        if (units == null) {
            return String.valueOf(windSpeed) + "m/s";
        }
        if (units.getWindSpeed() == WindSpeedUnit.MILES_PER_HOUR) {
            return String.valueOf(UnitUtils.metesPerSecondToMilesPerHour(windSpeed)) + "mph";
        }
        return String.valueOf(windSpeed) + "m/s";
    }

    public String getWindDirectionText() {
        return getWindDirection(windDegrees).name();
    }

    public String getHumidityText() {
        return String.valueOf(humidity) + "%";
    }

    private WindDirection getWindDirection(double degrees) {
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
