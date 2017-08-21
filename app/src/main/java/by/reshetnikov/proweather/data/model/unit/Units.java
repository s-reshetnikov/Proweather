package by.reshetnikov.proweather.data.model.unit;

import by.reshetnikov.proweather.data.preferences.units.DistanceUnits;
import by.reshetnikov.proweather.data.preferences.units.SpeedUnit;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;
import io.reactivex.subjects.PublishSubject;


public class Units {

    public PublishSubject<Units> unitsStream = PublishSubject.create();

    private TemperatureUnit temperature;
    private DistanceUnits distance;
    private SpeedUnit windSpeed;


    public Units(TemperatureUnit temperature, DistanceUnits distance, SpeedUnit windSpeed) {
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

    public SpeedUnit getWindSpeed() {
        return windSpeed;
    }
}
