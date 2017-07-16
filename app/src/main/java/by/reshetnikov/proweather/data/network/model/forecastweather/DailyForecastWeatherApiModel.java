package by.reshetnikov.proweather.data.network.model.forecastweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.network.model.Location;

public class DailyForecastWeatherApiModel {

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
    public List<DayForecastApiModel> dayForecasts;

    @SerializedName("location")
    @Expose
    public Location location;

    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("pressure")
    @Expose
    public float pressure;

    @SerializedName("humidity")
    @Expose
    public int humidity;

    @SerializedName("speed")
    @Expose
    public double windSpeed;

    @SerializedName("deg")
    @Expose
    public int degrees;

    @SerializedName("clouds")
    @Expose
    public int clouds;

    @SerializedName("rain")
    @Expose
    public float rain;

}