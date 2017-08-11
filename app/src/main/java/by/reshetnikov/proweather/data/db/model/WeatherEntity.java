package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class WeatherEntity {

    @Id(autoincrement = true)
    private long weatherId;
    private int weatherConditionId;
    private String weatherDescription;

    @Generated(hash = 815525583)
    public WeatherEntity(long weatherId, int weatherConditionId,
                         String weatherDescription) {
        this.weatherId = weatherId;
        this.weatherConditionId = weatherConditionId;
        this.weatherDescription = weatherDescription;
    }

    @Generated(hash = 1598697471)
    public WeatherEntity() {
    }

    public long getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(long Id) {
        this.weatherId = Id;
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

}
