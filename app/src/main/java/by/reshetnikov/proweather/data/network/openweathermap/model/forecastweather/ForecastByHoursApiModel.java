package by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.network.openweathermap.model.Clouds;
import by.reshetnikov.proweather.data.network.openweathermap.model.Main;
import by.reshetnikov.proweather.data.network.openweathermap.model.Snow;
import by.reshetnikov.proweather.data.network.openweathermap.model.Sys;
import by.reshetnikov.proweather.data.network.openweathermap.model.Weather;
import by.reshetnikov.proweather.data.network.openweathermap.model.Wind;

public class ForecastByHoursApiModel {

    // Time of data forecasted, unix, UTC
    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather;

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

    //  Data/time of calculation, UTC
    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;
}