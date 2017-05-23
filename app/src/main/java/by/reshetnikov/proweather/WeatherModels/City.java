package by.reshetnikov.proweather.WeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    // City ID
    @SerializedName("id")
    @Expose
    public int id;

    // City name
    @SerializedName("name")
    @Expose
    public String name;

    // City geo location
    @SerializedName("coord")
    @Expose
    public Coordinates coordinates;

    // Country code (GB, JP etc.)
    @SerializedName("country")
    @Expose
    public String country;

}