package by.reshetnikov.proweather.model.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {

    // Snow volume for the last 3 hours
    @SerializedName("3h")
    @Expose
    public double snowVolume;

}