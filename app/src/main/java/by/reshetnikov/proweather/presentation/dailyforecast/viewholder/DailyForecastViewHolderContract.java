package by.reshetnikov.proweather.presentation.dailyforecast.viewholder;

/**
 * Created by s-reshetnikov.
 */

public interface DailyForecastViewHolderContract {

    void setDate(String date);

    void setTemperature(String temperature);

    void setMainImage(int stateCode);

    void setHumidity(String humidity);

    void setPressure(String pressure);

    void setWindDetails(String windSpeed, String windDirection);

    void setMorningTemperature(String temperature);

    void setDayTemperature(String temperature);

    void setEveningTemperature(String temperature);

    void setNightTemperature(String temperature);

    boolean isExpanded();

    void expand();

    void collapse();
}
