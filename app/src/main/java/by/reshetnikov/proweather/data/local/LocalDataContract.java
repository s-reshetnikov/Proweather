package by.reshetnikov.proweather.data.local;


import android.location.Location;

import java.util.List;

import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.model.dbmodels.CityEntity;
import by.reshetnikov.proweather.model.dbmodels.ForecastEntity;
import by.reshetnikov.proweather.model.dbmodels.WeatherEntity;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataContract {

    Observable<WeatherEntity> getCurrentWeather(String cityId);

    Observable<ForecastEntity> getForecastWeather(String cityId);

    Observable<List<CityEntity>> getCities();

    Single<UnitsAppModel> getUnits();

    CityEntity getChosenCity();

    Location getCurrentLocation();

    void saveCity(CityEntity city);

    void saveCurrentWeather(WeatherEntity currentWeather);

    void saveForecastWeather(ForecastEntity forecastWeather);

    boolean canUseCurrentLocation();
}
