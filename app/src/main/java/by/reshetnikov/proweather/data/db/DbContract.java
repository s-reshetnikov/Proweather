package by.reshetnikov.proweather.data.db;

import android.support.annotation.Nullable;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface DbContract {

    Single<NowForecastEntity> getSavedNowForecast(@Nullable LocationEntity location);

    Completable saveCurrentWeather(NowForecastEntity currentWeather);

    Single<List<HoursForecastEntity>> getSavedHourlyForecast(LocationEntity location);

    Completable saveHourlyForecasts(List<HoursForecastEntity> forecastWeather);

    Single<List<DailyForecastEntity>> getSavedDailyForecast(LocationEntity location);

    Completable saveDailyForecasts(List<DailyForecastEntity> forecastWeather);

    Single<LocationEntity> getSavedLocation(long id);

    Single<LocationEntity> getChosenLocation();

    Single<List<LocationEntity>> getSavedLocations();

    Completable saveNewLocation(LocationEntity locationEntity);

    Completable updateLocation(LocationEntity locationEntity);

    Completable removeLocation(LocationEntity locationEntity);
}
