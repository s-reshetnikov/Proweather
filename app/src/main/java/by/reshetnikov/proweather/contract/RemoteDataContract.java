package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.model.apimodels.currentweather.CurrentWeather;
import by.reshetnikov.proweather.model.apimodels.forecastweather.HourlyForecastWeather;
import by.reshetnikov.proweather.model.apimodels.location.LocationForecast;
import io.reactivex.Single;


public interface RemoteDataContract {

    // current weather

    Single<CurrentWeather> getCurrentWeather(String locationId);

    Single<CurrentWeather> getCurrentWeather(double latitude, double longitude);

    // extended forecast

    Single<HourlyForecastWeather> getHourlyForecastWeather(String locationId);

    Single<HourlyForecastWeather> getHourlyForecastWeather(double latitude, double longitude);

    // daily forecast

    Single<HourlyForecastWeather> getDailyForecastWeather(String locationId);

    Single<HourlyForecastWeather> getDailyForecastWeather(double latitude, double longitude);

    // cities

    Single<LocationForecast> getLocations(String locationName, int count);

    Single<LocationForecast> getLocations(double latitude, double longitude, int count);

}
