package by.reshetnikov.proweather.data.network;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import io.reactivex.Single;

public interface WeatherApiDataContract {

    Single<CurrentForecastApiModel> getCurrentForecast(LocationEntity location);

    Single<HourlyForecastApiModel> getHourlyForecast(LocationEntity location);

    // Daily forecast is paid, hourly forecast will be used instead and transformed into daily
    Single<HourlyForecastApiModel> getDailyForecast(LocationEntity location);

    Single<LocationForecastApiModel> getLocationsByName(String locationName, int resultsCount);

    Single<LocationForecastApiModel> getLocationsByCoordinates(double latitude, double longitude, int resultsCount);
}