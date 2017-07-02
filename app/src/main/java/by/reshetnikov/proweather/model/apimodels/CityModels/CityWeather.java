package by.reshetnikov.proweather.model.apimodels.CityModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.model.apimodels.Clouds;
import by.reshetnikov.proweather.model.apimodels.Coordinates;
import by.reshetnikov.proweather.model.apimodels.Main;
import by.reshetnikov.proweather.model.apimodels.Rain;
import by.reshetnikov.proweather.model.apimodels.Snow;
import by.reshetnikov.proweather.model.apimodels.Sys;
import by.reshetnikov.proweather.model.apimodels.Weather;
import by.reshetnikov.proweather.model.apimodels.Wind;

public class CityWeather {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("coord")
    @Expose
    public Coordinates coordinates;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("rain")
    @Expose
    public Rain rain;

    @SerializedName("snow")
    @Expose
    public Snow snow;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather = null;

}
