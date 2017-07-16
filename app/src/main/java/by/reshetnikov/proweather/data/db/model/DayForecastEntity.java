package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DayForecastEntity {

    @Id(autoincrement = true)
    private long dayForecastId;
    private double dayTemperature;
    private double minTemperature;
    private double maxTemperature;
    private double nightTemperature;
    private double eveTemperature;
    private double mornTemperature;

    @Generated(hash = 155648546)
    public DayForecastEntity(long dayForecastId, double dayTemperature,
                             double minTemperature, double maxTemperature, double nightTemperature,
                             double eveTemperature, double mornTemperature) {
        this.dayForecastId = dayForecastId;
        this.dayTemperature = dayTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.nightTemperature = nightTemperature;
        this.eveTemperature = eveTemperature;
        this.mornTemperature = mornTemperature;
    }

    @Generated(hash = 426739695)
    public DayForecastEntity() {
    }

    public long getDayForecastId() {
        return dayForecastId;
    }

    public void setDayForecastId(long dayForecastId) {
        this.dayForecastId = dayForecastId;
    }

    public double getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public double getEveTemperature() {
        return eveTemperature;
    }

    public void setEveTemperature(double eveTemperature) {
        this.eveTemperature = eveTemperature;
    }

    public double getMornTemperature() {
        return mornTemperature;
    }

    public void setMornTemperature(double mornTemperature) {
        this.mornTemperature = mornTemperature;
    }
}
