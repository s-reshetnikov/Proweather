package by.reshetnikov.proweather.model.apimodels.forecastweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.model.apimodels.Location;

public class HourlyForecastWeather {

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
    public List<ForecastByHours> forecasts;

    @SerializedName("location")
    @Expose
    public Location location;

}