package by.reshetnikov.proweather.data.network.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    // Temperature. Unit Default: KELVIN, Metric: CELSIUS, Imperial: FAHRENHEIT.
    @SerializedName("temp")
    @Expose
    private double temperature;

    // Minimum temperature at the moment.
    @SerializedName("temp_min")
    @Expose
    private double tempMin;

    // Maximum temperature at the moment.
    @SerializedName("temp_max")
    @Expose
    private double tempMax;

    // Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
    @SerializedName("pressure")
    @Expose
    private double pressure;

    // Atmospheric pressure on the sea level, hPa
    @SerializedName("sea_level")
    @Expose
    private double seaLevel;

    // Atmospheric pressure on the ground level, hPa
    @SerializedName("grnd_level")
    @Expose
    private double grndLevel;

    // Humidity, %
    @SerializedName("humidity")
    @Expose
    private int humidity;


    @SerializedName("temp_kf")
    @Expose
    private double tempKf;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTempKf() {
        return tempKf;
    }

    public void setTempKf(int tempKf) {
        this.tempKf = tempKf;
    }
}