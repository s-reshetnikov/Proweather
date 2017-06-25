package by.reshetnikov.proweather.data;

import java.util.List;

import by.reshetnikov.proweather.data.appmodels.CityAppModel;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.ForecastWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.LocationAppModel;
import by.reshetnikov.proweather.data.appmodels.UnitsAppModel;
import io.reactivex.Observable;
import io.reactivex.Single;


public interface AppDataContract {

    Single<List<CityAppModel>> getSavedCities();

    void saveCity(CityAppModel city);

    boolean canUseCurrentLocation();

    LocationAppModel getCurrentLocation();

    Observable<UnitsAppModel> getUnits();

    Observable<CurrentWeatherAppModel> getCurrentWeather();

    Observable<ForecastWeatherAppModel> getForecastWeather(ForecastType forecastType);
}
