package by.reshetnikov.proweather.data.network.openweathermap.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OWMCoordinates {

    // LocationModel geo location, longitude
    @SerializedName("lon")
    @Expose
    private double longitude;
    // LocationModel geo location, latitude
    @SerializedName("lat")
    @Expose
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
