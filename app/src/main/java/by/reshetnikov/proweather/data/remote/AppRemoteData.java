package by.reshetnikov.proweather.data.remote;

import java.util.HashMap;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.model.apimodels.CityModels.CityForecast;
import by.reshetnikov.proweather.model.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.model.apimodels.ForecastWeatherModels.HourlyForecastWeather;
import by.reshetnikov.proweather.utils.ApiQuery;
import io.reactivex.Single;
import retrofit2.Retrofit;


public class AppRemoteData implements RemoteDataContract {

    private static final String TAG = AppRemoteData.class.getSimpleName();

    @Inject
    Retrofit retrofit;

    WeatherApi weatherApi;

    public AppRemoteData() {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
        weatherApi = retrofit.create(WeatherApi.class);
    }


    @Override
    public Single<CurrentWeather> getCurrentWeather(String locationId) {
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return weatherApi.getCurrentWeather(queryMap);
    }

    @Override
    public Single<CurrentWeather> getCurrentWeather(double latitude, double longitude) {
        HashMap<String, String> queryMap = new ApiQuery().addLatitudeAndLongitude(latitude, longitude).build();
        return weatherApi.getCurrentWeather(queryMap);
    }

    @Override
    public Single<HourlyForecastWeather> getHourlyForecastWeather(String locationId) {
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return weatherApi.getHourlyForecastWeather(queryMap);

    }

    @Override
    public Single<HourlyForecastWeather> getHourlyForecastWeather(double latitude, double longitude) {
        HashMap<String, String> queryMap = new ApiQuery().addLatitudeAndLongitude(latitude, longitude).build();
        return weatherApi.getHourlyForecastWeather(queryMap);
    }

    @Override
    public Single<HourlyForecastWeather> getDailyForecastWeather(String locationId) {
        HashMap<String, String> queryMap = new ApiQuery().addLocationId(locationId).build();
        return weatherApi.getDailyForecastWeather(queryMap);
    }

    @Override
    public Single<HourlyForecastWeather> getDailyForecastWeather(double latitude, double longitude) {
        HashMap<String, String> queryMap = new ApiQuery().addLatitudeAndLongitude(latitude, longitude).build();
        return weatherApi.getDailyForecastWeather(queryMap);
    }

    @Override
    public Single<CityForecast> getCities(String locationName, int count) {
        HashMap<String, String> queryMap = new ApiQuery()
                .addLocationName(locationName)
                .addCount(count)
                .searchTypeAccurate()
                .build();
        return weatherApi.getCities(queryMap);
    }

    @Override
    public Single<CityForecast> getCities(double latitude, double longitude, int count) {
        HashMap<String, String> queryMap = new ApiQuery()
                .addLatitudeAndLongitude(latitude, longitude)
                .addCount(count)
                .build();
        return weatherApi.getCities(queryMap);
    }

}
