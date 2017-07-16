package by.reshetnikov.proweather.data.network.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationForecastApiModel {

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
    public List<LocationWeatherApiModel> locationApiModelList;

}
