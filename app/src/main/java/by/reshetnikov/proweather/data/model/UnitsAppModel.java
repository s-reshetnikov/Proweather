package by.reshetnikov.proweather.data.model;

import by.reshetnikov.proweather.data.preferences.units.DistanceUnits;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;
import by.reshetnikov.proweather.data.preferences.units.WindSpeedUnit;
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
