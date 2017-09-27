package by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import by.reshetnikov.proweather.data.network.openweathermap.model.City;
import by.reshetnikov.proweather.data.network.openweathermap.model.LocationModel;

public class HourlyForecastApiModel {

    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("message")
    @Expose
    public double message;

    // Number of lines returned by this API call
    @SerializedName("cnt")
    @Expose
    public int cnt;

    @SerializedName("list")
    @Expose
    public List<ForecastByHoursApiModel> forecasts;

    @SerializedName("location")
    @Expose
    public City city;
    @SerializedName("city")
    @Expose
    public LocationModel location;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public List<ForecastByHoursApiModel> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastByHoursApiModel> forecasts) {
        this.forecasts = forecasts;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }
}