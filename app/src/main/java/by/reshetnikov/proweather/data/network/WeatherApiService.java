package by.reshetnikov.proweather.data.network;

import java.util.Map;

import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.DailyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.location.LocationForecastApiModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface WeatherApiService {

    @GET("data/2.5/weather")
    Observable<CurrentWeatherApiModel> getCurrentWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/forecast")
    Observable<HourlyForecastWeatherApiModel> getHourlyForecastWeather(@QueryMap Map<String, String> options);

    @GET("/data/2.5/forecast/daily")
    Observable<DailyForecastWeatherApiModel> getDailyForecastWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/find")
    Observable<LocationForecastApiModel> getLocations(@QueryMap Map<String, String> options);
}
