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
    private int locationId;
    private double temperature;
    private int humidity;

    @Unique
    private Date date;
    private int weatherConditionId;
    private String weatherDescription;
    private double windSpeed;
    private double windDegrees;
    private double rain;
    private double snow;

    @Generated(hash = 1191374903)
    public WeatherEntity(Long weatherId, int locationId, double temperature,
                         int humidity, Date date, int weatherConditionId,
                         String weatherDescription, double windSpeed, double windDegrees,
                         double rain, double snow) {
        this.weatherId = weatherId;
        this.locationId = locationId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.date = date;
        this.weatherConditionId = weatherConditionId;
        this.weatherDescription = weatherDescription;
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

    public void setWeatherId(long Id) {
        this.weatherId = Id;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

    public int getLocationId() {
        return this.locationId;
    }

    public void setLocationId(int locationId) {
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
