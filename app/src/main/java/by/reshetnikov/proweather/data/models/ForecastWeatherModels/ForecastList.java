package by.reshetnikov.proweather.data.models.ForecastWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.reshetnikov.proweather.data.models.Clouds;
import by.reshetnikov.proweather.data.models.Main;
import by.reshetnikov.proweather.data.models.Snow;
import by.reshetnikov.proweather.data.models.Sys;
import by.reshetnikov.proweather.data.models.Weather;
import by.reshetnikov.proweather.data.models.Wind;

public class ForecastList {

    // Time of data forecasted, unix, UTC
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

    //  Data/time of caluclation, UTC
    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;
}