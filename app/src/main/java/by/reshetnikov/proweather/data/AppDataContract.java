package by.reshetnikov.proweather.data;

import java.util.List;

import by.reshetnikov.proweather.data.appmodels.CityAppModel;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.ForecastWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.UnitsAppModel;
import io.reactivex.Single;


public interface AppDataContract {

    Single<List<CityAppModel>> getSavedCities();

    void saveCity(CityAppModel city);

    boolean canUseCurrentLocation();

    Single<UnitsAppModel> getUnits();

    Single<CurrentWeatherAppModel> getCurrentWeather();

    Single<ForecastWeatherAppModel> getForecastWeather();
}
