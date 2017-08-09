package by.reshetnikov.proweather.data.network.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    //  Wind speed
    @SerializedName("speed")
    @Expose
    private double speed;

    // Wind direction, degrees (meteorological)
    @SerializedName("deg")
    @Expose
    private double degrees;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }
}