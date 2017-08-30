package by.reshetnikov.proweather.data.model.weather.nowforecast;

import android.support.annotation.NonNull;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.utils.CalendarUtil;
import by.reshetnikov.proweather.utils.UnitUtils;

/**
 * Created by s-reshetnikov.
 */

public class NowForecastViewModel {

    private NowForecastEntity forecast;
    private LocationEntity location;
    private Units units;

    public NowForecastViewModel(@NonNull NowForecastEntity forecast) {
        this(forecast, null, null);
    }

    public NowForecastViewModel(@NonNull NowForecastEntity forecast, Units units, LocationEntity location) {
        this.forecast = forecast;
        this.location = location;
        this.units = units;
    }

    public void applyUnits(Units units) {
        this.units = units;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public String getTemperature() {
        if (units != null)
            return UnitUtils.getTemperatureWithUnits(forecast.getTemperature(), units.getTemperature());
        return UnitUtils.getTemperatureWithUnits(forecast.getTemperature(), null);
    }

    public String getWindSpeed() {
        if (units != null)
            return UnitUtils.getSpeed(forecast.getWindSpeed(), units.getWindSpeed());
        return UnitUtils.getSpeed(forecast.getWindSpeed(), null);
    }

    public int getWeatherIconId() {
        return forecast.getWeatherConditionId();
    }

    public int getWindDegrees() {
        return forecast.getWindDirectionDegrees();
    }

    public String getWindDirection() {
        return UnitUtils.getWindDirection(forecast.getWindDirectionDegrees());
    }

    public String getWeekDay() {
        return CalendarUtil.getTodayWeekDay();
    }

    public String getLocationName() {
        if (location != null)
            return location.getLocationName();
        return "";
    }

    public String getWeatherDescription() {
        return forecast.getWeatherDescription();
    }

    public String getHumidity() {
        return forecast.getHumidity() + " %";
    }
}
