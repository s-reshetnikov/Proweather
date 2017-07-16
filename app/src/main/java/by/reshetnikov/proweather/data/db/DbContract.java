package by.reshetnikov.proweather.data.db;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.CurrentWeatherEntity;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import io.reactivex.Observable;

public interface DbContract {

    Observable<CurrentWeatherEntity> getCurrentWeather(String locationId);

    Observable<Boolean> saveCurrentWeather(CurrentWeatherEntity currentWeather);

    Observable<HourlyForecastEntity> getHourlyForecastWeather(String locationId);

    Observable<DailyForecastEntity> getDailyForecastWeather(String locationId);

    Observable<Boolean> saveHourlyForecastWeather(HourlyForecastEntity forecastWeather);

    Observable<LocationEntity> getChosenLocation();

    Observable<List<LocationEntity>> getSavedLocations();

    Observable<Boolean> saveNewLocation(LocationEntity locationEntity);

    Observable<Boolean> updateLocation(LocationEntity locationEntity);

    Observable<Boolean> removeLocation(LocationEntity locationEntity);
}
