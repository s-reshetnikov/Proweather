package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeather;
import by.reshetnikov.proweather.data.network.model.location.LocationForecast;


public interface RemoteDataContract {

    // current weather

    CurrentWeather getCurrentWeather(String locationId);

    CurrentWeather getCurrentWeather(double latitude, double longitude);

    // extended forecast

    HourlyForecastWeather getHourlyForecastWeather(String locationId);

    HourlyForecastWeather getHourlyForecastWeather(double latitude, double longitude);

    // daily forecast

    DailyForecastWeather getDailyForecastWeather(String locationId);

    DailyForecastWeather getDailyForecastWeather(double latitude, double longitude);

    // cities

    LocationForecast getLocations(String locationName, int count);

    LocationForecast getLocations(double latitude, double longitude, int count);

}
