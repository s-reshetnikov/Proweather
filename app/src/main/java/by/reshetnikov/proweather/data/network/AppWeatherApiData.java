package by.reshetnikov.proweather.data.network;

import android.text.TextUtils;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.HashMap;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.data.network.openweathermap.OpenWeatherMapApiService;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.DailyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import by.reshetnikov.proweather.utils.ApiQuery;
import io.reactivex.Single;


public class AppWeatherApiData implements WeatherApiDataContract {

    private static final String TAG = AppWeatherApiData.class.getSimpleName();

    private OpenWeatherMapApiService api;

    @Inject
    public AppWeatherApiData(OpenWeatherMapApiService api) {
        Log.d(TAG, "create AppWeatherApiData");
        this.api = api;
    }

    @Override
    public Single<CurrentWeatherApiModel> getCurrentWeather(LocationContract location) {
        HashMap<String, String> queryMap = getQueryForRequest(location);
        return api.getCurrentWeather(queryMap);
    }

    @Override
    public Single<HourlyForecastWeatherApiModel> getHourlyForecastWeather(LocationContract location) {
        HashMap<String, String> queryMap = getQueryForRequest(location);
        return api.getHourlyForecastWeather(queryMap);
    }


    @Override
    public Single<DailyForecastWeatherApiModel> getDailyForecastWeather(LocationContract location) {
        HashMap<String, String> queryMap = getQueryForRequest(location);
        return api.getDailyForecastWeather(queryMap);
    }

    @Override
    public Single<LocationForecastApiModel> getLocationsByName(String locationName, int maxResults) {
        Log.d(TAG, "getSavedDailyForecastWeather location " + locationName + " call");
        HashMap<String, String> queryMap = getQuery(locationName, maxResults);
        return api.getLocations(queryMap);
    }

    @Override
    public Single<LocationForecastApiModel> getLocationsByCoordinates(int latitude, int longitude, int maxResults) {
        HashMap<String, String> queryMap = new ApiQuery().addLatitudeAndLongitude(latitude, longitude)
                .addCount(maxResults).build();
        return api.getLocations(queryMap);
    }

    private HashMap<String, String> getQueryForRequest(LocationContract location) {
        int maxResultsPerRequest = 10;
        return getQueryForRequest(location, maxResultsPerRequest);
    }

    private HashMap<String, String> getQueryForRequest(LocationContract location, int maxResults) {

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
        return new ApiQuery().addLocationName(locationName).addCount(maxResults).build();
    }

    private HashMap<String, String> getQuery(double latitude, double longitude, int maxResults) {
        return new ApiQuery().addLatitudeAndLongitude(latitude, longitude).addCount(maxResults).build();
    }

}
