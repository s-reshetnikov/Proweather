package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class NowForecastEntity {

    @Id
    private Long currentWeatherId;
    private String locationId;
    private int weatherConditionId;
    private String weatherDescription;
    private int temperature;
    private int rain;
    private int snow;
    private int humidity;
    private double windSpeed;
    private int windDirectionDegrees;
    private long dateOfUpdate;


    @Generated(hash = 254857214)
    public NowForecastEntity(Long currentWeatherId, String locationId,
                             int weatherConditionId, String weatherDescription, int temperature,
                             int rain, int snow, int humidity, double windSpeed,
                             int windDirectionDegrees, long dateOfUpdate) {
        this.currentWeatherId = currentWeatherId;
        this.locationId = locationId;
        this.weatherConditionId = weatherConditionId;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.rain = rain;
        this.snow = snow;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirectionDegrees = windDirectionDegrees;
        this.dateOfUpdate = dateOfUpdate;
    }

    @Generated(hash = 1365494212)
    public NowForecastEntity() {
    }


    public Long getCurrentWeatherId() {
        return currentWeatherId;
    }

    public void setCurrentWeatherId(Long currentWeatherId) {
        this.currentWeatherId = currentWeatherId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getRain() {
        return rain;
    }

    public void setRain(int rain) {
        this.rain = rain;
    }

    public int getSnow() {
        return snow;
    }

    public void setSnow(int snow) {
        this.snow = snow;
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

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirectionDegrees() {
        return windDirectionDegrees;
    }

    public void setWindDirectionDegrees(int degrees) {
        this.windDirectionDegrees = degrees;
    }

    public long getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(long dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public int getHumidity() {
        return this.humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}