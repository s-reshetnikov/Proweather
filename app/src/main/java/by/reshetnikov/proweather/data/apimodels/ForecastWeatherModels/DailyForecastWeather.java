package by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.apimodels.City;

public class DailyForecastWeather {

    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("message")
    @Expose
    public double message;

    // Number of lines returned by this API call
    @SerializedName("cnt")
    @Expose
    public int cnt;

    @SerializedName("list")
    @Expose
    public List<DayForecast> dayForecasts;

    @SerializedName("city")
    @Expose
    public City city;

}