package by.reshetnikov.proweather.data.network.openweathermap;

import java.util.Map;

import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface OpenWeatherMapApiService {

    @GET("data/2.5/weather")
    Single<CurrentForecastApiModel> getCurrentWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/forecast")
    Single<HourlyForecastApiModel> getHourlyForecastWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/find")
    Single<LocationForecastApiModel> getLocations(@QueryMap Map<String, String> options);
}
