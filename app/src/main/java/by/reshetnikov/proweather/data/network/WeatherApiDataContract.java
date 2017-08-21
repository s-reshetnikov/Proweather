package by.reshetnikov.proweather.data.network;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import io.reactivex.Single;

// TODO: implement independence from API source
public interface WeatherApiDataContract {

    Single<CurrentForecastApiModel> getCurrentForecast(LocationEntity location);

    Single<HourlyForecastApiModel> getHourlyForecast(LocationEntity location);

    Single<LocationForecastApiModel> getLocationsByName(String locationName, int resultsCount);

    Single<LocationForecastApiModel> getLocationsByCoordinates(int latitude, int longitude, int resultsCount);
}