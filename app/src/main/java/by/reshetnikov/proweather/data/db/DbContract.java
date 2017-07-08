package by.reshetnikov.proweather.data.db;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.WeatherEntity;
import io.reactivex.Observable;

public interface DbContract {

    Observable<WeatherEntity> getCurrentWeather(String locationId);

    Observable<Boolean> saveCurrentWeather(WeatherEntity currentWeather);

    Observable<DailyForecastEntity> getForecastWeather(String locationId);

    Observable<Boolean> saveForecastWeather(DailyForecastEntity forecastWeather);

    Observable<LocationEntity> getChosenLocation();

    Observable<List<LocationEntity>> getSavedLocations();

    Observable<Boolean> saveNewLocation(LocationEntity locationEntity);

    Observable<Boolean> updateLocation(LocationEntity locationEntity);

    Observable<Boolean> removeLocation(LocationEntity locationEntity);
}
