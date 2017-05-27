package by.reshetnikov.proweather;

import java.util.Map;

import by.reshetnikov.proweather.WeatherModels.CurrentWetherModels.CurrentWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface NetworkService {

    @GET("data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(@QueryMap Map<String, String> options, @Query("appid") String appId);

    @GET("data/2.5/forecast")
    Call<CurrentWeather> getForecastWeather(@QueryMap Map<String, String> options, @Query("appid") String appId);

}
