package by.reshetnikov.proweather.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type")
    @Expose
    public int type;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("message")
    @Expose
    public double message;

    @SerializedName("pod")
    @Expose
    public String pod;

    // Country code (GB, JP etc.)
    @SerializedName("country")
    @Expose
    public String country;

    // Sunrise time, unix, UTC
    @SerializedName("sunrise")
    @Expose
    public int sunrise;

    // Sunset time, unix, UTC
    @SerializedName("sunset")
    @Expose
    public int sunset;

}