package by.reshetnikov.proweather.WeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    //  Wind speed
    @SerializedName("speed")
    @Expose
    public double speed;

    // Wind direction, degrees (meteorological)
    @SerializedName("deg")
    @Expose
    public int degrees;

}