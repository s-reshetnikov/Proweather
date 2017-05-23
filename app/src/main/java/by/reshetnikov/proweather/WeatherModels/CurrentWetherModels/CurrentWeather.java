package by.reshetnikov.proweather.WeatherModels.CurrentWetherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.WeatherModels.Clouds;
import by.reshetnikov.proweather.WeatherModels.Coordinates;
import by.reshetnikov.proweather.WeatherModels.Main;
import by.reshetnikov.proweather.WeatherModels.Rain;
import by.reshetnikov.proweather.WeatherModels.Sys;
import by.reshetnikov.proweather.WeatherModels.Weather;
import by.reshetnikov.proweather.WeatherModels.Wind;

public class CurrentWeather {

    @SerializedName("coord")
    @Expose
    public Coordinates coordinates;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather = null;

    @SerializedName("base")
    @Expose
    public String base;

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

    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("cod")
    @Expose
    public int cod;

}