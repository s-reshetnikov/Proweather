package by.reshetnikov.proweather.contract;


import android.location.Location;

import java.util.List;

import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.model.dbmodels.ForecastEntity;
import by.reshetnikov.proweather.model.dbmodels.LocationEntity;
import by.reshetnikov.proweather.model.dbmodels.WeatherEntity;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataContract {

    Observable<WeatherEntity> getCurrentWeather(String locationId);

    Observable<ForecastEntity> getForecastWeather(String locationId);

    Single<List<LocationEntity>> getLocations();

    Single<UnitsAppModel> getUnits();

    LocationEntity getChosenLocation();

    Location getCurrentLocation();

    void saveLocation(LocationAppModel location);

    void saveCurrentWeather(WeatherEntity currentWeather);

    void saveForecastWeather(ForecastEntity forecastWeather);

    boolean canUseCurrentLocation();
}
