package by.reshetnikov.proweather.WeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    // Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @SerializedName("temp")
    @Expose
    public double temperature;

    //Minimum temperature at the moment.
    @SerializedName("temp_min")
    @Expose
    public double tempMin;

    // Maximum temperature at the moment.
    @SerializedName("temp_max")
    @Expose
    public double tempMax;

    //Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
    @SerializedName("pressure")
    @Expose
    public int pressure;


    @SerializedName("sea_level")
    @Expose
    public double seaLevel;


    @SerializedName("grnd_level")
    @Expose
    public int grndLevel;

    // Humidity, %
    @SerializedName("humidity")
    @Expose
    public int humidity;


    @SerializedName("temp_kf")
    @Expose
    public int tempKf;

}