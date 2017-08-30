package by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.network.openweathermap.model.Clouds;
import by.reshetnikov.proweather.data.network.openweathermap.model.Main;
import by.reshetnikov.proweather.data.network.openweathermap.model.Rain;
import by.reshetnikov.proweather.data.network.openweathermap.model.Snow;
import by.reshetnikov.proweather.data.network.openweathermap.model.Sys;
import by.reshetnikov.proweather.data.network.openweathermap.model.Weather;
import by.reshetnikov.proweather.data.network.openweathermap.model.Wind;

public class ForecastByHoursApiModel {

    // Time of data forecasted, unix, UTC
    @SerializedName("dt")
    @Expose
    public long date;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("weather")
    @Expose
    public List<Weather> weather;
    @SerializedName("clouds")
    @Expose
    public Clouds clouds;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("snow")
    @Expose
    public Snow snow;
    @SerializedName("rain")
    @Expose
    public Rain rain;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    //  Data/time of calculation, UTC
    @SerializedName("dt_txt")
    @Expose
    public String dateText;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather getWeather() {
        return weather.get(0);
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
}