package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;


public class ForecastByHoursEntity {

    @Id(autoincrement = true)
    private long forecastByHoursId;
    private int dateTime;
    @ToMany(referencedJoinProperty = "weatherId")
    private List<WeatherEntity> weather;
    private double snow;
    private double rain;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private double windDegrees;

    public long getForecastByHoursId() {
        return forecastByHoursId;
    }

    public void setForecastByHoursId(long forecastByHoursId) {
        this.forecastByHoursId = forecastByHoursId;
    }

    public int getDateTime() {
        return dateTime;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
    }

    public double getSnow() {
        return snow;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegrees() {
        return windDegrees;
    }

    public void setWindDegrees(double windDegrees) {
        this.windDegrees = windDegrees;
    }
}
