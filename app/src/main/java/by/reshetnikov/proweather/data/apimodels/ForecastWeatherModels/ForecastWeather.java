package by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.reshetnikov.proweather.data.apimodels.City;

public class ForecastWeather {

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
    public java.util.List<ForecastList> list = null;

    @SerializedName("city")
    @Expose
    public City city;

}