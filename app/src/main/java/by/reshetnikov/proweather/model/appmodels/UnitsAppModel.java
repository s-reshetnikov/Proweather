package by.reshetnikov.proweather.model.appmodels;

import by.reshetnikov.proweather.model.units.DistanceUnits;
import by.reshetnikov.proweather.model.units.TemperatureUnit;
import by.reshetnikov.proweather.model.units.WindSpeedUnit;
import io.reactivex.subjects.PublishSubject;


public class UnitsAppModel {

    public PublishSubject<UnitsAppModel> unitsStream = PublishSubject.create();

    private TemperatureUnit temperature;
    private DistanceUnits distance;
    private WindSpeedUnit windSpeed;


    public UnitsAppModel(TemperatureUnit temperature, DistanceUnits distance, WindSpeedUnit windSpeed) {
        this.temperature = temperature;
        this.distance = distance;
        this.windSpeed = windSpeed;
    }

    public TemperatureUnit getTemperature() {
        return temperature;
    }

    public DistanceUnits getDistance() {
        return distance;
    }

    public WindSpeedUnit getWindSpeed() {
        return windSpeed;
    }

}
