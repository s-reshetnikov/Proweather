package by.reshetnikov.proweather.data.network.openweathermap;

import java.util.Map;

import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.DailyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface OpenWeatherMapApiService {

    @GET("data/2.5/weather")
    Single<CurrentWeatherApiModel> getCurrentWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/forecast")
    Single<HourlyForecastWeatherApiModel> getHourlyForecastWeather(@QueryMap Map<String, String> options);

    @GET("/data/2.5/forecast/daily")
    Single<DailyForecastWeatherApiModel> getDailyForecastWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/find")
    Single<LocationForecastApiModel> getLocations(@QueryMap Map<String, String> options);
}
