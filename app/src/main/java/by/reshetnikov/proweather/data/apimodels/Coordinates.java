package by.reshetnikov.proweather.data.apimodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

    // City geo location, longitude
    @SerializedName("lon")
    @Expose
    public double longitude;

    // City geo location, latitude
    @SerializedName("lat")
    @Expose
    public double latitude;

}
