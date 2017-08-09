package by.reshetnikov.proweather.data.model.unit;

import by.reshetnikov.proweather.data.preferences.units.DistanceUnits;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;
import by.reshetnikov.proweather.data.preferences.units.WindSpeedUnit;
import io.reactivex.subjects.PublishSubject;


public class UnitsAppModel implements UnitsContract {

    public PublishSubject<UnitsContract> unitsStream = PublishSubject.create();

    private TemperatureUnit temperature;
    private DistanceUnits distance;
    private WindSpeedUnit windSpeed;


    public UnitsAppModel(TemperatureUnit temperature, DistanceUnits distance, WindSpeedUnit windSpeed) {
        this.temperature = temperature;
        this.distance = distance;
        this.windSpeed = windSpeed;
    }

    @Override
    public TemperatureUnit getTemperature() {
        return temperature;
    }

    @Override
    public DistanceUnits getDistance() {
        return distance;
    }

    @Override
    public WindSpeedUnit getWindSpeed() {
        return windSpeed;
    }

}
