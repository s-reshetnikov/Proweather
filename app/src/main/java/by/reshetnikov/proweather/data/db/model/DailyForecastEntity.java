package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity
public class DailyForecastEntity {

    @Id(autoincrement = true)
    private long dailyForecastId;
    @ToMany(referencedJoinProperty = "dayForecastId")
    private List<DayForecastEntity> dayForecasts;
    private String locationId;
    private int pressure;
    private double humidity;
    private double rain;
    private double snow;
    private double clouds;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1638140434)
    private transient DailyForecastEntityDao myDao;

    @Generated(hash = 1815357949)
    public DailyForecastEntity(long dailyForecastId, String locationId,
                               int pressure, double humidity, double rain, double snow,
                               double clouds) {
        this.dailyForecastId = dailyForecastId;
        this.locationId = locationId;
        this.pressure = pressure;
        this.humidity = humidity;
        this.rain = rain;
        this.snow = snow;
        this.clouds = clouds;
    }

    @Generated(hash = 994273881)
    public DailyForecastEntity() {
    }

    public long getDailyForecastId() {
        return dailyForecastId;
    }

    public void setDailyForecastId(long dailyForecastId) {
        this.dailyForecastId = dailyForecastId;
    }

    public String getLocation() {
        return locationId;
    }

    public void setLocation(String locationId) {
        this.locationId = locationId;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2083969154)
    public List<DayForecastEntity> getDayForecasts() {
        if (dayForecasts == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DayForecastEntityDao targetDao = daoSession.getDayForecastEntityDao();
            List<DayForecastEntity> dayForecastsNew = targetDao
                    ._queryDailyForecastEntity_DayForecasts(dailyForecastId);
            synchronized (this) {
                if (dayForecasts == null) {
                    dayForecasts = dayForecastsNew;
                }
            }
        }
        return dayForecasts;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1095106974)
    public synchronized void resetDayForecasts() {
        dayForecasts = null;
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
    @Generated(hash = 2106318919)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDailyForecastEntityDao() : null;
    }
}
