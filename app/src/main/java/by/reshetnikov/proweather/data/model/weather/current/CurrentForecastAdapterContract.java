package by.reshetnikov.proweather.data.model.weather.current;


import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.model.unit.UnitsAppModel;
import by.reshetnikov.proweather.data.model.unit.UnitsContract;

public interface CurrentForecastAdapterContract {

    void applyUnits(UnitsContract units);

    CurrentForecastEntity getAdaptee();

    double getRain();

    double getSnow();

    int getWeatherConditionId();

    double getWindDegrees();

    int getHumidity();

    double getWindSpeed();

    void applyUnits(UnitsAppModel units);

    double getTemperature();

    double getPrecipitation();
}
