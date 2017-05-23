package by.reshetnikov.proweather.WeatherModels.ForecastWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.reshetnikov.proweather.WeatherModels.Clouds;
import by.reshetnikov.proweather.WeatherModels.Main;
import by.reshetnikov.proweather.WeatherModels.Snow;
import by.reshetnikov.proweather.WeatherModels.Sys;
import by.reshetnikov.proweather.WeatherModels.Weather;
import by.reshetnikov.proweather.WeatherModels.Wind;

public class ForecastList {

    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("weather")
    @Expose
    public java.util.List<Weather> weather = null;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("snow")
    @Expose
    public Snow snow;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;
}