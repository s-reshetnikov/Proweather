package by.reshetnikov.proweather.data.local.db.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;


@Entity
public class CurrentWeatherEntity {

    @Id(autoincrement = true)
    private long Id;
    @Index
    @NotNull
    private String cityId;
    private double temperature;
    private int humidity;
    private int pressure;
    @Unique
    private Date date;
    private int weatherConditionId;
    private String weatherMain;
    private String weatherDescription;
    private String iconCode;
    private double windSpeed;
    private double windDegrees;
    private double rain;
    private double snow;

    @Generated(hash = 50537360)
    public CurrentWeatherEntity(long Id, @NotNull String cityId, double temperature,
                                int humidity, int pressure, Date date, int weatherConditionId,
                                String weatherMain, String weatherDescription, String iconCode,
                                double windSpeed, double windDegrees, double rain, double snow) {
        this.Id = Id;
        this.cityId = cityId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.date = date;
        this.weatherConditionId = weatherConditionId;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.iconCode = iconCode;
        this.windSpeed = windSpeed;
        this.windDegrees = windDegrees;
        this.rain = rain;
        this.snow = snow;
    }

    @Generated(hash = 1937976266)
    public CurrentWeatherEntity() {
    }

    public long getId() {
        return this.Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getWeatherMain() {
        return this.weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
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
