package by.reshetnikov.proweather.data.model.unit;

import by.reshetnikov.proweather.data.preferences.units.DistanceUnits;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;
import by.reshetnikov.proweather.data.preferences.units.WindSpeedUnit;

/**
 * Created by SacRahl on 8/9/2017.
 */

public interface UnitsContract {
    TemperatureUnit getTemperature();

    DistanceUnits getDistance();

    WindSpeedUnit getWindSpeed();
}
