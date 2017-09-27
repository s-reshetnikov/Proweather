package by.reshetnikov.proweather.data.network;

import android.text.TextUtils;

import java.security.InvalidParameterException;
import java.util.HashMap;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.network.openweathermap.OpenWeatherMapApiService;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import by.reshetnikov.proweather.utils.ApiQuery;
import io.reactivex.Single;
import timber.log.Timber;


public class AppWeatherApiData implements WeatherApiDataContract {

    private OpenWeatherMapApiService api;

    @Inject
    public AppWeatherApiData(OpenWeatherMapApiService api) {
        Timber.d("createNowForecastFromAPI AppWeatherApiData");
        this.api = api;
    }

    @Override
    public Single<CurrentForecastApiModel> getCurrentForecast(LocationEntity location) {
        HashMap<String, String> queryMap = getQueryForRequest(location);
        return api.getCurrentWeather(queryMap);
    }

    @Override
    public Single<HourlyForecastApiModel> getHourlyForecast(LocationEntity location) {
        HashMap<String, String> queryMap = getQueryForRequest(location);
        return api.getHourlyForecastWeather(queryMap);
    }

    @Override
    public Single<HourlyForecastApiModel> getDailyForecast(LocationEntity location) {
        HashMap<String, String> queryMap = getQueryForRequest(location, 0);
        return api.getHourlyForecastWeather(queryMap);
    }

    @Override
    public Single<LocationForecastApiModel> getLocationsByName(String locationName, int maxResults) {
        Timber.d("getSavedDailyForecast location " + locationName + " call");
        HashMap<String, String> queryMap = getQuery(locationName, maxResults);
        return api.getLocations(queryMap);
    }

    @Override
    public Single<LocationForecastApiModel> getLocationsByCoordinates(double latitude, double longitude, int maxResults) {
        HashMap<String, String> queryMap = new ApiQuery().addLatitudeAndLongitude(latitude, longitude)
                .addCount(maxResults).build();
        return api.getLocations(queryMap);
    }

    private HashMap<String, String> getQueryForRequest(LocationEntity location) {
        int maxResultsPerRequest = 10;
        return getQueryForRequest(location, maxResultsPerRequest);
    }

    private HashMap<String, String> getQueryForRequest(LocationEntity location, int maxResults) {

        if (!TextUtils.isEmpty(location.getLocationName())) {
            if (TextUtils.isEmpty(location.getCountryCode()))
                return getQuery(location.getLocationName() + "," + location.getCountryCode(), maxResults);
            return getQuery(location.getLocationName(), maxResults);
        }
        if (location.getLatitude() != 0 && location.getLongitude() != 0)
            return getQuery(location.getLatitude(), location.getLatitude(), maxResults);
        throw new InvalidParameterException("No such query builder");
    }

    private HashMap<String, String> getQuery(int locationId) {
        return new ApiQuery().addLocationId(String.valueOf(locationId)).build();
    }

    private HashMap<String, String> getQuery(String locationName, int maxResults) {
        if (maxResults == 0)
            return new ApiQuery().addLocationName(locationName).build();
        return new ApiQuery().addLocationName(locationName).addCount(maxResults).build();
    }

    private HashMap<String, String> getQuery(double latitude, double longitude, int maxResults) {
        return new ApiQuery().addLatitudeAndLongitude(latitude, longitude).addCount(maxResults).build();
    }

}
