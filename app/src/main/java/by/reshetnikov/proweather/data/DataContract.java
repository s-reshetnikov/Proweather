package by.reshetnikov.proweather.data;

import java.util.List;

import javax.annotation.Nonnull;

import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.model.Coordinates;
import by.reshetnikov.proweather.data.model.unit.Units;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;


public interface DataContract {


    Single<NowForecastEntity> getNowForecast(@Nonnull LocationEntity location);

    Single<List<HoursForecastEntity>> getHourForecasts(@Nonnull LocationEntity location);

    Observable<Coordinates> getBalancedPowerLastCoordinates();

    Observable<Coordinates> getLowPowerLastCoordinates();

    Single<List<LocationEntity>> getAllLocationsByName(String locationName, int resultsCount);

    Single<List<LocationEntity>> getLocationsByCoordinates(double latitude, double longitude, int resultsCount);

    Single<LocationEntity> getChosenLocation();

    Single<List<LocationEntity>> getSavedLocations();

    Completable saveNewLocation(@Nonnull LocationEntity location);

    Completable saveOrUpdateLocation(@NonNull LocationEntity location);

    Completable removeLocation(@Nonnull LocationEntity location);

    boolean canUseCurrentLocation();

    Single<Units> getUnits();

    Completable updateLocationPositions(@NonNull List<LocationEntity> locations);

    Single<List<DailyForecastEntity>> getDailyForecasts(@Nonnull LocationEntity location);

    Single<Coordinates> getSavedLastLocations();

    CompletableSource saveLastLocation(Coordinates coordinates);

    Single<Coordinates> getLastSavedLocation();
}
