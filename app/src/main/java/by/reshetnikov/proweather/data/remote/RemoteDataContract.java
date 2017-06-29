package by.reshetnikov.proweather.data.remote;

import by.reshetnikov.proweather.data.apimodels.CityModels.CityForecast;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.HourlyForecastWeather;
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

    Single<CityForecast> getCities(String locationName, int count);

    Single<CityForecast> getCities(double latitude, double longitude, int count);

}
