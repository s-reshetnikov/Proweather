package by.reshetnikov.proweather.data.remote;

import java.util.Map;

import by.reshetnikov.proweather.model.apimodels.CityModels.CityForecast;
import by.reshetnikov.proweather.model.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.model.apimodels.ForecastWeatherModels.HourlyForecastWeather;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface WeatherApi {

    @GET("data/2.5/weather")
    Single<CurrentWeather> getCurrentWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/forecast")
    Single<HourlyForecastWeather> getHourlyForecastWeather(@QueryMap Map<String, String> options);

    @GET("/data/2.5/forecast/daily")
    Single<HourlyForecastWeather> getDailyForecastWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/find")
    Single<CityForecast> getCities(@QueryMap Map<String, String> options);
}
