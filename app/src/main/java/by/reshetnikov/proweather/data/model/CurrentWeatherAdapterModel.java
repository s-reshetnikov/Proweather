package by.reshetnikov.proweather.data.model;

import by.reshetnikov.proweather.data.db.model.CurrentWeatherEntity;


public class CurrentWeatherAdapterModel implements CurrentWeatherAdapterContract {

    private final CurrentWeatherEntity entity;

    private UnitsAppModel units;

    public CurrentWeatherAdapterModel(CurrentWeatherEntity entity) {
        this.entity = entity;
    }

    @Override
    public CurrentWeatherEntity getAdaptee() {
        return entity;
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
        return entity.getWeatherEntity().getWeatherConditionId();
    }

    @Override
    public void setWeatherConditionId(int weatherConditionId) {
        entity.getWeatherEntity().setWeatherConditionId(weatherConditionId);
    }

    @Override
    public String getWeatherDescription() {
        return entity.getWeatherEntity().getWeatherDescription();
    }

    @Override
    public void setWeatherDescription(String weatherDescription) {
        entity.getWeatherEntity().setWeatherDescription(weatherDescription);
    }

    @Override
    public String getIconCode() {
        return entity.getWeatherEntity().getIconCode();
    }

    @Override
    public void setIconCode(String iconCode) {
        entity.getWeatherEntity().setIconCode(iconCode);
    }

    @Override
    public double getWindDegrees() {
        return entity.getWindDirectionDegrees();
    }

    @Override
    public void setWindDegrees(int windDegrees) {
        entity.setWindDirectionDegrees(windDegrees);
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
