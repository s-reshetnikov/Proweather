package by.reshetnikov.proweather.data.model.weather.hourly;

import java.util.Date;

import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;

/**
 * Created by SacRahl on 8/9/2017.
 */

public class HourlyForecastAdapter implements HourlyForecastAdapterContract {

    private HourlyForecastEntity entity;

    public HourlyForecastAdapter(HourlyForecastEntity entity) {
        this.entity = entity;
    }

    @Override
    public HourlyForecastEntity getAdaptee() {
        return entity;
    }

    @Override
    public int getLocationId() {
        return entity.getLocationId();
    }

    @Override
    public double getTemperature() {
        return entity.getTemperature();
    }

    @Override
    public int getHumidity() {
        return entity.getHumidity();
    }

    @Override
    public int getPressure() {
        return entity.getPressure();
    }

    @Override
    public double getWindSpeed() {
        return entity.getWindSpeed();
    }

    @Override
    public double getWindDegrees() {
        return entity.getWindDegrees();
    }

    @Override
    public Date getDate() {
        return entity.getDate();
    }

    @Override
    public double getSnow() {
        return entity.getSnow();
    }

    @Override
    public double getRain() {
        return entity.getRain();
    }

    @Override
    public int getWeatherConditionId() {
        return entity.getWeatherEntity().getWeatherConditionId();
    }

    @Override
    public String getWeatherDescription() {
        return entity.getWeatherEntity().getWeatherDescription();
    }
}
