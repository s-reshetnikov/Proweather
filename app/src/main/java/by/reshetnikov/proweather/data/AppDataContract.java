package by.reshetnikov.proweather.data;

import java.util.List;

import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.data.appmodels.CityAppModel;
import io.reactivex.Observable;
import io.reactivex.Single;


public interface AppDataContract {

    Observable<CurrentWeather> getCurrentWeather();

    Observable<ForecastWeather> getForecastWeather(ForecastType forecastType);

    Single<List<CityAppModel>> getSavedCities();

    void saveCity(CityAppModel city);

    boolean canUseCurrentLocation();

    TemperatureUnits getTemperatureUnit();

    DistanceUnits getDistanceUnit();

    WindSpeedUnits getWindSpeedUnit();

}
