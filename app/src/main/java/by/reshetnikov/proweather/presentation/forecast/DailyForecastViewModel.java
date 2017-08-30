package by.reshetnikov.proweather.presentation.forecast;

import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.utils.CalendarUtil;
import by.reshetnikov.proweather.utils.UnitUtils;

/**
 * Created by s-reshetnikov.
 */

public class DailyForecastViewModel {

    private static final char WHITESPACE = ' ';
    private static final char PERCENT = '%';
    private static final char DASH = '-';
    private static final char COMMA = ',';

    private DailyForecastEntity entity;

    private Units units;

    public DailyForecastViewModel(DailyForecastEntity entity) {
        this.entity = entity;
    }

    public void applyUnits(Units units) {
        this.units = units;
    }

    public String getDate() {
        return CalendarUtil.getWeekDay(entity.getDate()) + COMMA +
                WHITESPACE + CalendarUtil.getLocaleDayAndMonth(entity.getDate());
    }

    public String getTemperature() {
        return UnitUtils.getTemperature(entity.getMinTemperature(), units.getTemperature()) + WHITESPACE + DASH + WHITESPACE +
                UnitUtils.getTemperatureWithUnits(entity.getMaxTemperature(), units.getTemperature());
    }

    public String getWindSpeed() {
        return UnitUtils.getSpeed(entity.getWindSpeed(), units.getWindSpeed());
    }

    public String getWindDirection() {
        return UnitUtils.getWindDirection(entity.getWindDegrees());
    }

    public int getDailyIconCode() {
        return entity.getWeatherConditionId();
    }

    public String getHumidity() {
        return String.valueOf(entity.getHumidity()) + PERCENT;
    }

    public String getPressure() {
        return UnitUtils.getPressureWithUnits(entity.getPressure());
    }

    public String getMorningTemperature() {
        return UnitUtils.getTemperatureWithUnits(entity.getMorningTemperature(), units.getTemperature());
    }

    public String getDayTemperature() {
        return UnitUtils.getTemperatureWithUnits(entity.getDayTemperature(), units.getTemperature());
    }

    public String getEveningTemperature() {
        return UnitUtils.getTemperatureWithUnits(entity.getEveningTemperature(), units.getTemperature());
    }

    public String getNightTemperature() {
        return UnitUtils.getTemperatureWithUnits(entity.getNightTemperature(), units.getTemperature());
    }
}
