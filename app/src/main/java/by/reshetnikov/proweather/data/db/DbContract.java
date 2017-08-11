package by.reshetnikov.proweather.data.db;

import android.support.annotation.Nullable;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.model.location.LocationContract;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface DbContract {

    Single<CurrentForecastEntity> getSavedCurrentWeather(@Nullable LocationContract location);

    Completable saveCurrentWeather(CurrentForecastEntity currentWeather);

    Single<HourlyForecastEntity> getSavedHourlyForecast(LocationContract location);

    Completable saveHourlyForecastWeather(HourlyForecastEntity forecastWeather);

    Single<LocationEntity> getChosenLocation();

    Single<List<LocationEntity>> getSavedLocations();

    Completable saveNewLocation(LocationEntity locationEntity);

    Completable updateLocation(LocationEntity locationEntity);

    Completable removeLocation(LocationEntity locationEntity);
}
