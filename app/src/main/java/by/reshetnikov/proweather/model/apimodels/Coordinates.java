package by.reshetnikov.proweather.model.apimodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

    // Location geo location, longitude
    @SerializedName("lon")
    @Expose
    public double longitude;

    // Location geo location, latitude
    @SerializedName("lat")
    @Expose
    public double latitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
