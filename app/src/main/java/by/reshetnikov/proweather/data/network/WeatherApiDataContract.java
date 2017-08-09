package by.reshetnikov.proweather.data.network;

import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.DailyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import io.reactivex.Single;

// TODO: implement independence from API source
public interface WeatherApiDataContract {

    Single<CurrentWeatherApiModel> getCurrentWeather(LocationContract location);

    Single<HourlyForecastWeatherApiModel> getHourlyForecastWeather(LocationContract location);

    Single<DailyForecastWeatherApiModel> getDailyForecastWeather(LocationContract location);

    Single<LocationForecastApiModel> getLocationsByName(String locationName, int resultsCount);

    Single<LocationForecastApiModel> getLocationsByCoordinates(int latitude, int longitude, int resultsCount);
}