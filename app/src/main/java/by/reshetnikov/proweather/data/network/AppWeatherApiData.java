package by.reshetnikov.proweather.data.network;

import android.util.Log;

import java.util.HashMap;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeather;
import by.reshetnikov.proweather.data.network.model.location.LocationForecast;
import by.reshetnikov.proweather.utils.ApiQuery;
import io.reactivex.Observable;


public class AppWeatherApiData implements WeatherApiDataContract {

    private static final String TAG = AppWeatherApiData.class.getSimpleName();

    @Inject
    WeatherApiService api;

    public AppWeatherApiData() {
        Log.d(TAG, "create AppWeatherApiData");
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getCurrentWeather(String locationId) {
        Log.d(TAG, "getCurrentWeather location " + locationId + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return api.getCurrentWeather(queryMap);
    }

    @Override
    public Observable<HourlyForecastWeather> getHourlyForecastWeather(String locationId) {
        Log.d(TAG, "getHourlyForecastWeather location " + locationId + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return api.getHourlyForecastWeather(queryMap);
    }

    @Override
    public Observable<DailyForecastWeather> getDailyForecastWeather(String locationId) {
        Log.d(TAG, "getDailyForecastWeather location " + locationId + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return api.getDailyForecastWeather(queryMap);
    }

    @Override
    public Observable<LocationForecast> getLocationsByName(String locationName) {
        Log.d(TAG, "getDailyForecastWeather location " + locationName + " call");
        HashMap<String, String> queryMap = new ApiQuery().addLocationName(locationName).addCount(10).build();
        return api.getLocations(queryMap);
    }
}
