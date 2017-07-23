package by.reshetnikov.proweather.data.network;

import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.location.LocationForecastApiModel;
import io.reactivex.Observable;


public interface WeatherApiDataContract {

    Observable<CurrentWeatherApiModel> getCurrentWeather(String locationId);

    Observable<HourlyForecastWeatherApiModel> getHourlyForecastWeather(String locationId);

    Observable<DailyForecastWeatherApiModel> getDailyForecastWeather(String locationId);

    Observable<LocationForecastApiModel> getLocationsByName(String locationName, int resultsCount);
}
