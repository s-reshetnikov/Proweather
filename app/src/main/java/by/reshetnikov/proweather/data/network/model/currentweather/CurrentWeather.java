package by.reshetnikov.proweather.data.network.model.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.network.model.Clouds;
import by.reshetnikov.proweather.data.network.model.Coordinates;
import by.reshetnikov.proweather.data.network.model.Main;
import by.reshetnikov.proweather.data.network.model.Rain;
import by.reshetnikov.proweather.data.network.model.Snow;
import by.reshetnikov.proweather.data.network.model.Sys;
import by.reshetnikov.proweather.data.network.model.Weather;
import by.reshetnikov.proweather.data.network.model.Wind;

public class CurrentWeather {

    @SerializedName("coord")
    @Expose
    public Coordinates coordinates;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("rain")
    @Expose
    public Rain rain;

    @SerializedName("snow")
    @Expose
    public Snow snow;

    //  Time of data calculation, unix, UTC
    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    // Location ID
    @SerializedName("id")
    @Expose
    public int id;

    // Location name
    @SerializedName("name")
    @Expose
    public String name;
}