package by.reshetnikov.proweather.data.network;

import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeather;
import by.reshetnikov.proweather.data.network.model.location.LocationForecast;
import io.reactivex.Observable;


public interface WeatherApiDataContract {


    Observable<CurrentWeather> getCurrentWeather(String locationId);

    Observable<HourlyForecastWeather> getHourlyForecastWeather(String locationId);

    Observable<DailyForecastWeather> getDailyForecastWeather(String locationId);

    Observable<LocationForecast> getLocationsByName(String locationName);
}
