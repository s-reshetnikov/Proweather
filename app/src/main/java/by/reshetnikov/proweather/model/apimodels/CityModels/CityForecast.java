package by.reshetnikov.proweather.model.apimodels.CityModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityForecast {

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("count")
    @Expose
    public int count;

    @SerializedName("list")
    @Expose
    public List<CityForecast> cityForecasts;

}
