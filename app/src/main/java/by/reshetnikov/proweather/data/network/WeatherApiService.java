package by.reshetnikov.proweather.data.network;

import java.util.Map;

import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeather;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeather;
import by.reshetnikov.proweather.data.network.model.location.LocationForecast;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface WeatherApiService {

    @GET("data/2.5/weather")
    Observable<CurrentWeather> getCurrentWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/forecast")
    Observable<HourlyForecastWeather> getHourlyForecastWeather(@QueryMap Map<String, String> options);

    @GET("/data/2.5/forecast/daily")
    Observable<DailyForecastWeather> getDailyForecastWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/find")
    Observable<LocationForecast> getLocations(@QueryMap Map<String, String> options);
}
