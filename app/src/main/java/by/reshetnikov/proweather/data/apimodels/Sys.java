package by.reshetnikov.proweather.data.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    // TODO: description?
    @SerializedName("type")
    @Expose
    public int type;

    // TODO: description?
    @SerializedName("id")
    @Expose
    public int id;

    // TODO: description?
    @SerializedName("message")
    @Expose
    public double message;

    // TODO: description?
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