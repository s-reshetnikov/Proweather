package by.reshetnikov.proweather.data.network.openweathermap.model.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.network.openweathermap.model.Clouds;
import by.reshetnikov.proweather.data.network.openweathermap.model.Main;
import by.reshetnikov.proweather.data.network.openweathermap.model.OWMCoordinates;
import by.reshetnikov.proweather.data.network.openweathermap.model.Rain;
import by.reshetnikov.proweather.data.network.openweathermap.model.Snow;
import by.reshetnikov.proweather.data.network.openweathermap.model.Sys;
import by.reshetnikov.proweather.data.network.openweathermap.model.Weather;
import by.reshetnikov.proweather.data.network.openweathermap.model.Wind;

public class CurrentForecastApiModel {

    @SerializedName("coord")
    @Expose
    private OWMCoordinates coordinates;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather;

    @SerializedName("main")
    @Expose
    private Main main;

    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("clouds")
    @Expose
    private Clouds clouds;

    @SerializedName("rain")
    @Expose
    private Rain rain;

    @SerializedName("snow")
    @Expose
    private Snow snow;

    // Time of data calculation, unix, UTC
    @SerializedName("dt")
    @Expose
    private long date;

    @SerializedName("sys")
    @Expose
    private Sys sys;

    // Location ID
    @SerializedName("id")
    @Expose
    private int id;
    // Location name
    @SerializedName("name")
    @Expose
    private String name;

    public OWMCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(OWMCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Weather getWeather() {
        return weather.get(0);
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long dt) {
        this.date = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}