package by.reshetnikov.proweather.model.apimodels.ForecastWeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.model.apimodels.Clouds;
import by.reshetnikov.proweather.model.apimodels.Main;
import by.reshetnikov.proweather.model.apimodels.Snow;
import by.reshetnikov.proweather.model.apimodels.Sys;
import by.reshetnikov.proweather.model.apimodels.Weather;
import by.reshetnikov.proweather.model.apimodels.Wind;

public class ForecastByHours {

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

    //  Data/time of caluclation, UTC
    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;
}