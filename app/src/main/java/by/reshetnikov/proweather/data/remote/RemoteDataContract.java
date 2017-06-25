package by.reshetnikov.proweather.data.remote;

import by.reshetnikov.proweather.data.ForecastType;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import io.reactivex.Observable;


public interface RemoteDataContract {

    Observable<CurrentWeather> getCurrentWeather();

    Observable<ForecastWeather> getForecastWeather(ForecastType forecastType);
}
