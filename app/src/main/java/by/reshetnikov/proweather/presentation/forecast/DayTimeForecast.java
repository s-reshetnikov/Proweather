package by.reshetnikov.proweather.presentation.forecast;

import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.utils.UnitUtils;

/**
 * Created by s-reshetnikov.
 */

public class DayTimeForecast {

    private Units units;
    private int iconCode;
    private int temperature;
    private int windDegrees;
    private double windSpeed;

    public DayTimeForecast(Units units) {
        this.units = units;
    }

    public int getIconCode() {
        return iconCode;
    }

    public void setIconCode(int iconCode) {
        this.iconCode = iconCode;
    }

    public String getTemperature() {
        return UnitUtils.getTemperatureWithUnits(temperature, units.getTemperature());
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public float getWindDegrees() {
        return windDegrees;
    }

    public void setWindDegrees(int windDegrees) {
        this.windDegrees = windDegrees;
    }

    public String getWindDirection() {
        return UnitUtils.getWindDirection(windDegrees);
    }

    public String getWindSpeed() {
        return UnitUtils.getSpeed(windSpeed, units.getWindSpeed());
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
