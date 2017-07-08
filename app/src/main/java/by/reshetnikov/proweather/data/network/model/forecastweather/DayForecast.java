package by.reshetnikov.proweather.data.network.model.forecastweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayForecast {
    @SerializedName("day")
    @Expose
    public double day;

    @SerializedName("min")
    @Expose
    public double min;

    @SerializedName("max")
    @Expose
    public double max;

    @SerializedName("night")
    @Expose
    public double night;

    @SerializedName("eve")
    @Expose
    public double eve;

    @SerializedName("morn")
    @Expose
    public double morn;
}
