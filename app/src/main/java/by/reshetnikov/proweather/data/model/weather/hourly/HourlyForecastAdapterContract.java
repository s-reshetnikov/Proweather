package by.reshetnikov.proweather.data.model.weather.hourly;

import java.util.Date;

import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;

/**
 * Created by SacRahl on 8/9/2017.
 */

public interface HourlyForecastAdapterContract {
    HourlyForecastEntity getAdaptee();

    int getLocationId();

    double getTemperature();

    int getHumidity();

    int getPressure();

    double getWindSpeed();

    double getWindDegrees();

    Date getDate();

    double getSnow();

    double getRain();

    int getWeatherConditionId();

    String getWeatherDescription();
}
