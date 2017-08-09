package by.reshetnikov.proweather.data.network.openweathermap.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.reshetnikov.proweather.data.network.openweathermap.model.Coordinates;
import by.reshetnikov.proweather.data.network.openweathermap.model.Sys;

public class LocationWeatherApiModel {

    @SerializedName("id")
    @Expose
    private int locationId;
    @SerializedName("name")
    @Expose
    private String locationName;
    @SerializedName("coord")
    @Expose
    private Coordinates coordinates;
    @SerializedName("sys")
    @Expose
    private Sys sys;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {

        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return coordinates.getLatitude();
    }

    public double getLongitude() {
        return coordinates.getLongitude();
    }

    public String getCountryCode() {
        return sys.getCountry();
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }


}
