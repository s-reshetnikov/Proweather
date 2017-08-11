package by.reshetnikov.proweather.data;

import android.support.annotation.NonNull;

import java.util.List;

import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.data.model.unit.UnitsContract;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapterContract;
import by.reshetnikov.proweather.data.model.weather.hourly.HourlyForecastAdapterContract;
import io.reactivex.Completable;
import io.reactivex.Single;


public interface DataContract {


    Single<? extends CurrentForecastAdapterContract> getSavedCurrentWeather(@NonNull LocationContract location);

    Single<? extends HourlyForecastAdapterContract> getSavedHourlyForecastWeather(@NonNull LocationContract location);

    Single<List<LocationContract>> getAllLocationsByName(String locationName, int resultsCount);

    Single<? extends LocationContract> getChosenLocation();

    Single<List<LocationContract>> getSavedLocations();

    Completable saveNewLocation(@NonNull LocationContract location);

    Completable removeLocation(@NonNull LocationContract location);

    boolean getCanUseCurrentLocationPreference();

    Single<UnitsContract> getUnits();

    Completable updateLocationPosition(int fromPosition, int toPosition);
}
