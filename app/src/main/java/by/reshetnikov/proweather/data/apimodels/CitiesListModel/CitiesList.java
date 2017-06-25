package by.reshetnikov.proweather.data.apimodels.CitiesListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.apimodels.Clouds;
import by.reshetnikov.proweather.data.apimodels.Coordinates;
import by.reshetnikov.proweather.data.apimodels.Main;
import by.reshetnikov.proweather.data.apimodels.Rain;
import by.reshetnikov.proweather.data.apimodels.Snow;
import by.reshetnikov.proweather.data.apimodels.Sys;
import by.reshetnikov.proweather.data.apimodels.Weather;
import by.reshetnikov.proweather.data.apimodels.Wind;

public class CitiesList {

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
