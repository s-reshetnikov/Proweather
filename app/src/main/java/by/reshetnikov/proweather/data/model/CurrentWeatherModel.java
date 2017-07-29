package by.reshetnikov.proweather.data.model;

import by.reshetnikov.proweather.data.db.model.WeatherEntity;


public class CurrentWeatherModel implements CurrentWeatherModelContract {

    private final WeatherEntity entity;

    private UnitsAppModel units;

    public CurrentWeatherModel(WeatherEntity entity) {
        this.entity = entity;
    }

    @Override
    public double getRain() {
        return entity.getRain();
    }

    @Override
    public void setRain(double rain) {
        entity.setRain(rain);
    }

    @Override
    public double getSnow() {
        return entity.getSnow();
    }

    @Override
    public void setSnow(double snow) {
        entity.setSnow(snow);
    }

    @Override
    public int getPressure() {
        return entity.getPressure();
    }

    @Override
    public void setPressure(int pressure) {
        entity.setPressure(pressure);
    }

    @Override
    public int getWeatherConditionId() {
        return entity.getWeatherConditionId();
    }

    @Override
    public void setWeatherConditionId(int weatherConditionId) {
        entity.setWeatherConditionId(weatherConditionId);
    }

    @Override
    public String getWeatherDescription() {
        return entity.getWeatherDescription();
    }

    @Override
    public void setWeatherDescription(String weatherDescription) {
        entity.setWeatherDescription(weatherDescription);
    }

    @Override
    public String getIconCode() {
        return entity.getIconCode();
    }

    @Override
    public void setIconCode(String iconCode) {
        entity.setIconCode(iconCode);
    }

    @Override
    public double getWindDegrees() {
        return entity.getWindDegrees();
    }

    @Override
    public void setWindDegrees(double windDegrees) {
        entity.setWindDegrees(windDegrees);
    }

    @Override
    public int getHumidity() {
        return entity.getHumidity();
    }

    @Override
    public void setHumidity(int humidity) {
        entity.setHumidity(humidity);
    }

    @Override
    public double getWindSpeed() {
        return entity.getWindSpeed();
    }

    @Override
    public void setWindSpeed(double windSpeed) {
        entity.setWindSpeed(windSpeed);
    }

    @Override
    public void applyUnits(UnitsAppModel units) {
        this.units = units;
    }

    @Override
    public double getTemperature() {
        return entity.getTemperature();
    }

    @Override
    public void setTemperature(double temperature) {
        entity.setTemperature(temperature);
    }
}