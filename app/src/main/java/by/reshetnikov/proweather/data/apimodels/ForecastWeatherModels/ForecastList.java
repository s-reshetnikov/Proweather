package by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.reshetnikov.proweather.data.apimodels.Clouds;
import by.reshetnikov.proweather.data.apimodels.Main;
import by.reshetnikov.proweather.data.apimodels.Snow;
import by.reshetnikov.proweather.data.apimodels.Sys;
import by.reshetnikov.proweather.data.apimodels.Weather;
import by.reshetnikov.proweather.data.apimodels.Wind;

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