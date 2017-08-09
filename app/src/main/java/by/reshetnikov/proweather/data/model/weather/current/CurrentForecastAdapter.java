package by.reshetnikov.proweather.data.model.weather.current;

import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.model.unit.UnitsAppModel;
import by.reshetnikov.proweather.data.model.unit.UnitsContract;


public class CurrentForecastAdapter implements CurrentForecastAdapterContract {

    private final CurrentForecastEntity entity;

    private UnitsContract units;

    public CurrentForecastAdapter(CurrentForecastEntity entity) {
        this.entity = entity;
    }

    @Override
    public void applyUnits(UnitsContract units) {
        this.units = units;
    }

    @Override
    public CurrentForecastEntity getAdaptee() {
        return entity;
    }

    @Override
    public double getRain() {
        return entity.getRain();
    }

    @Override
    public double getSnow() {
        return entity.getSnow();
    }

    @Override
    public int getWeatherConditionId() {
        return entity.getWeatherEntity().getWeatherConditionId();
    }

    @Override
    public double getWindDegrees() {
        return entity.getWindDirectionDegrees();
    }

    @Override
    public int getHumidity() {
        return entity.getHumidity();
    }

    @Override
    public double getWindSpeed() {
        return entity.getWindSpeed();
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
    public double getPrecipitation() {
        return getRain() + getSnow();
    }
}
