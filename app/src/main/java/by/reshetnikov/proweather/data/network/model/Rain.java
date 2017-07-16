package by.reshetnikov.proweather.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    // Rain volume for the last 3 hours
    @SerializedName("3h")
    @Expose
    private double rainVolume;

    public double getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(double rainVolume) {
        this.rainVolume = rainVolume;
    }

}