package by.reshetnikov.proweather.data;

import android.support.annotation.NonNull;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.model.unit.Units;
import io.reactivex.Completable;
import io.reactivex.Single;


public interface DataContract {


    Single<NowForecastEntity> getNowForecast(@NonNull LocationEntity location);

    Single<List<HoursForecastEntity>> getHourForecasts(@NonNull LocationEntity location);

    Single<List<LocationEntity>> getAllLocationsByName(String locationName, int resultsCount);

    Single<LocationEntity> getChosenLocation();

    Single<List<LocationEntity>> getSavedLocations();

    Completable saveNewLocation(@NonNull LocationEntity location);

    Completable removeLocation(@NonNull LocationEntity location);

    boolean getCanUseCurrentLocationPreference();

    Single<Units> getUnits();

    Completable updateLocationPosition(int fromPosition, int toPosition);
}
