package by.reshetnikov.proweather.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {

    // Snow volume for the last 3 hours
    @SerializedName("3h")
    @Expose
    private double snowVolume;

    public double getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(double snowVolume) {
        this.snowVolume = snowVolume;
    }
}