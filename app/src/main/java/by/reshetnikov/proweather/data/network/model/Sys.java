package by.reshetnikov.proweather.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("message")
    @Expose
    private double message;

    @SerializedName("pod")
    @Expose
    private String pod;

    // Country code (GB, JP etc.)
    @SerializedName("country")
    @Expose
    private String country;

    // Sunrise time, unix, UTC
    @SerializedName("sunrise")
    @Expose
    private int sunrise;

    // Sunset time, unix, UTC
    @SerializedName("sunset")
    @Expose
    private int sunset;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}