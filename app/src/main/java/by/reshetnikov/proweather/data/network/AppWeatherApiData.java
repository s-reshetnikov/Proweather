package by.reshetnikov.proweather.data.network;

import android.util.Log;

import java.util.HashMap;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.location.LocationForecastApiModel;
import by.reshetnikov.proweather.utils.ApiQuery;
import io.reactivex.Observable;


public class AppWeatherApiData implements WeatherApiDataContract {

    private static final String TAG = AppWeatherApiData.class.getSimpleName();

    private WeatherApiService api;

    @Inject
    public AppWeatherApiData(WeatherApiService api) {
        Log.d(TAG, "create AppWeatherApiData");
        this.api = api;
    }

    @Override
    public Observable<CurrentWeatherApiModel> getCurrentWeather(String locationId) {
        Log.d(TAG, "getCurrentWeather location " + locationId + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return api.getCurrentWeather(queryMap);
    }

    @Override
    public Observable<HourlyForecastWeatherApiModel> getHourlyForecastWeather(String locationId) {
        Log.d(TAG, "getHourlyForecastWeather location " + locationId + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return api.getHourlyForecastWeather(queryMap);
    }

    @Override
    public Observable<DailyForecastWeatherApiModel> getDailyForecastWeather(String locationId) {
        Log.d(TAG, "getDailyForecastWeather location " + locationId + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return api.getDailyForecastWeather(queryMap);
    }

    @Override
    public Observable<LocationForecastApiModel> getLocationsByName(String locationName, int resultsCount) {
        Log.d(TAG, "getDailyForecastWeather location " + locationName + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationName(locationName).addCount(resultsCount).build();
        return api.getLocations(queryMap);
    }
}
