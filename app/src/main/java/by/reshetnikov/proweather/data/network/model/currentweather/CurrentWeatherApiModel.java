package by.reshetnikov.proweather.data.network.model.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.network.model.Clouds;
import by.reshetnikov.proweather.data.network.model.Coordinates;
import by.reshetnikov.proweather.data.network.model.Main;
import by.reshetnikov.proweather.data.network.model.Rain;
import by.reshetnikov.proweather.data.network.model.Snow;
import by.reshetnikov.proweather.data.network.model.Sys;
import by.reshetnikov.proweather.data.network.model.Weather;
import by.reshetnikov.proweather.data.network.model.Wind;

public class CurrentWeatherApiModel {

    @SerializedName("coord")
    @Expose
    private Coordinates coordinates;

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
    private int dt;

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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
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

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
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