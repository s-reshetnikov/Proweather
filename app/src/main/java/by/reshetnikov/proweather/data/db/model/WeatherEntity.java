package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;


@Entity
public class WeatherEntity {

    @Id(autoincrement = true)
    private Long weatherId;
    @Index
    @NotNull
    private String locationId;
    private double temperature;
    private int humidity;
    private int pressure;
    @Unique
    private Date date;
    private int weatherConditionId;
    private String weatherDescription;
    private String iconCode;
    private double windSpeed;
    private double windDegrees;
    private double rain;
    private double snow;

    @Generated(hash = 1751359941)
    public WeatherEntity(Long weatherId, @NotNull String locationId,
                         double temperature, int humidity, int pressure, Date date,
                         int weatherConditionId, String weatherDescription, String iconCode,
                         double windSpeed, double windDegrees, double rain, double snow) {
        this.weatherId = weatherId;
        this.locationId = locationId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.date = date;
        this.weatherConditionId = weatherConditionId;
        this.weatherDescription = weatherDescription;
        this.iconCode = iconCode;
        this.windSpeed = windSpeed;
        this.windDegrees = windDegrees;
        this.rain = rain;
        this.snow = snow;
    }

    @Generated(hash = 1598697471)
    public WeatherEntity() {
    }

    public Long getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

    public void setWeatherId(long Id) {
        this.weatherId = Id;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return this.humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return this.pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWeatherConditionId() {
        return this.weatherConditionId;
    }

    public void setWeatherConditionId(int weatherConditionId) {
        this.weatherConditionId = weatherConditionId;
    }

    public String getWeatherDescription() {
        return this.weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getIconCode() {
        return this.iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public double getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegrees() {
        return this.windDegrees;
    }

    public void setWindDegrees(double windDegrees) {
        this.windDegrees = windDegrees;
    }

    public double getRain() {
        return this.rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return this.snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }
}
