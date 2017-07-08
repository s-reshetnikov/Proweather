package by.reshetnikov.proweather.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    // Cloudiness, %
    @SerializedName("all")
    @Expose
    public int cloudiness;

}
