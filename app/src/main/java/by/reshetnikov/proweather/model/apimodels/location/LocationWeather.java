package by.reshetnikov.proweather.model.apimodels.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.reshetnikov.proweather.model.apimodels.Coordinates;
import by.reshetnikov.proweather.model.apimodels.Sys;

public class LocationWeather {

    @SerializedName("id")
    @Expose
    public String locationId;

    @SerializedName("name")
    @Expose
    public String locationName;

    @SerializedName("coord")
    @Expose
    public Coordinates coordinates;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getCountryCode() {
        return sys.country;
    }

}
