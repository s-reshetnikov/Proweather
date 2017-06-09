package by.reshetnikov.proweather.data.remote;

import java.util.Map;

import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.data.models.ForecastWeatherModels.ForecastWeather;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface WeatherApi {

    @GET("data/2.5/weather")
    Observable<CurrentWeather> getCurrentWeather(@QueryMap Map<String, String> options);

    @GET("data/2.5/forecast")
    Observable<ForecastWeather> getForecastWeather(@QueryMap Map<String, String> options);

}
