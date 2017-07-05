package by.reshetnikov.proweather.model.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    // Location ID
    @SerializedName("id")
    @Expose
    public int id;

    // Location name
    @SerializedName("name")
    @Expose
    public String name;

    // Location geo location
    @SerializedName("coord")
    @Expose
    public Coordinates coordinates;

    // Country code (GB, JP etc.)
    @SerializedName("country")
    @Expose
    public String country;

}