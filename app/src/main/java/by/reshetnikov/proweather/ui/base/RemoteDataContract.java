package by.reshetnikov.proweather.ui.base;

import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;


public interface RemoteDataContract {

    // current weather

    CurrentForecastApiModel getCurrentWeather(String locationId);

    CurrentForecastApiModel getCurrentWeather(double latitude, double longitude);

    // extended forecast

    HourlyForecastApiModel getHourlyForecastWeather(String locationId);

    HourlyForecastApiModel getHourlyForecastWeather(double latitude, double longitude);

    // cities

    LocationForecastApiModel getLocations(String locationName, int count);

    LocationForecastApiModel getLocations(double latitude, double longitude, int count);

}
