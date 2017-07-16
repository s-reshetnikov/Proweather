package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.location.LocationForecastApiModel;


public interface RemoteDataContract {

    // current weather

    CurrentWeatherApiModel getCurrentWeather(String locationId);

    CurrentWeatherApiModel getCurrentWeather(double latitude, double longitude);

    // extended forecast

    HourlyForecastWeatherApiModel getHourlyForecastWeather(String locationId);

    HourlyForecastWeatherApiModel getHourlyForecastWeather(double latitude, double longitude);

    // daily forecast

    DailyForecastWeatherApiModel getDailyForecastWeather(String locationId);

    DailyForecastWeatherApiModel getDailyForecastWeather(double latitude, double longitude);

    // cities

    LocationForecastApiModel getLocations(String locationName, int count);

    LocationForecastApiModel getLocations(double latitude, double longitude, int count);

}
