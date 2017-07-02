package by.reshetnikov.proweather.contracts;

import java.util.List;

import by.reshetnikov.proweather.model.appmodels.CityAppModel;
import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.ForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import io.reactivex.Single;


public interface AppDataContract {

    Single<List<CityAppModel>> getSavedCities();

    void saveCity(CityAppModel city);

    boolean canUseCurrentLocation();

    Single<UnitsAppModel> getUnits();

    Single<CurrentWeatherAppModel> getCurrentWeather();

    Single<ForecastWeatherAppModel> getForecastWeather();

    void dispose();
}
