package by.reshetnikov.proweather.data.db.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class HoursForecastEntity {

    @Id
    private Long date;
    private int locationId;
    private int temperature;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private int windDegrees;
    private int weatherConditionId;
    private String weatherDescription;
    private double snow;
    private double rain;

    @Generated(hash = 1959683428)
    public HoursForecastEntity(Long date, int locationId, int temperature,
                               int humidity, int pressure, double windSpeed, int windDegrees,
                               int weatherConditionId, String weatherDescription, double snow,
                               double rain) {
        this.date = date;
        this.locationId = locationId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDegrees = windDegrees;
        this.weatherConditionId = weatherConditionId;
        this.weatherDescription = weatherDescription;
        this.snow = snow;
        this.rain = rain;
    }

    @Generated(hash = 377572766)
    public HoursForecastEntity() {
    }

    public int getLocationId() {
        return this.locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
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

    public double getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDegrees() {
        return this.windDegrees;
    }

    public void setWindDegrees(int windDegrees) {
        this.windDegrees = windDegrees;
    }

    public Long getDate() {
        return this.date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getSnow() {
        return this.snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public int getWeatherConditionId() {
        return weatherConditionId;
    }

    public void setWeatherConditionId(int weatherConditionId) {
        this.weatherConditionId = weatherConditionId;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
