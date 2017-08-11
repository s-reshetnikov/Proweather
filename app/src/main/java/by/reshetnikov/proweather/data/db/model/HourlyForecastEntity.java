package by.reshetnikov.proweather.data.db.model;


import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;


@Entity(active = true)
public class HourlyForecastEntity {
    @Id(autoincrement = true)
    private long hourlyForecastEntityId;
    private int locationId;
    private double temperature;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private double windDegrees;
    @Unique
    private Date date;
    @ToOne(joinProperty = "hourlyForecastEntityId")
    private WeatherEntity weatherEntity;
    private double snow;
    private double rain;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1006291059)
    private transient HourlyForecastEntityDao myDao;

    @Generated(hash = 1767295460)
    public HourlyForecastEntity(long hourlyForecastEntityId, int locationId,
                                double temperature, int humidity, int pressure, double windSpeed,
                                double windDegrees, Date date, double snow, double rain) {
        this.hourlyForecastEntityId = hourlyForecastEntityId;
        this.locationId = locationId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDegrees = windDegrees;
        this.date = date;
        this.snow = snow;
        this.rain = rain;
    }

    @Generated(hash = 27515208)
    public HourlyForecastEntity() {
    }

    @Generated(hash = 1942152839)
    private transient Long weatherEntity__resolvedKey;

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

    public double getWindDegrees() {
        return this.windDegrees;
    }

    public void setWindDegrees(double windDegrees) {
        this.windDegrees = windDegrees;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
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

    public long getHourlyForecastEntityId() {
        return this.hourlyForecastEntityId;
    }

    public void setHourlyForecastEntityId(long hourlyForecastEntityId) {
        this.hourlyForecastEntityId = hourlyForecastEntityId;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1026688404)
    public WeatherEntity getWeatherEntity() {
        long __key = this.hourlyForecastEntityId;
        if (weatherEntity__resolvedKey == null
                || !weatherEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WeatherEntityDao targetDao = daoSession.getWeatherEntityDao();
            WeatherEntity weatherEntityNew = targetDao.load(__key);
            synchronized (this) {
                weatherEntity = weatherEntityNew;
                weatherEntity__resolvedKey = __key;
            }
        }
        return weatherEntity;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1548406284)
    public void setWeatherEntity(@NotNull WeatherEntity weatherEntity) {
        if (weatherEntity == null) {
            throw new DaoException(
                    "To-one property 'hourlyForecastEntityId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.weatherEntity = weatherEntity;
            hourlyForecastEntityId = weatherEntity.getWeatherId();
            weatherEntity__resolvedKey = hourlyForecastEntityId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 384525640)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHourlyForecastEntityDao() : null;
    }
}
