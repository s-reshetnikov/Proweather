package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity(active = true)
public class CurrentWeatherEntity {

    @Id
    private long currentWeatherId;
    private String locationId;
    @ToMany(referencedJoinProperty = "weatherId")
    private List<WeatherEntity> weathers;
    private double temperature;
    private double rain;
    private double snow;
    private double clouds;
    private int pressure;
    private double windSpeed;
    private int degrees;
    private int dateOfUpdate;
    private int sunrise;
    private int sunset;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 643943492)
    private transient CurrentWeatherEntityDao myDao;

    @Generated(hash = 894542767)
    public CurrentWeatherEntity(long currentWeatherId, String locationId, double temperature,
                                double rain, double snow, double clouds, int pressure, double windSpeed, int degrees,
                                int dateOfUpdate, int sunrise, int sunset) {
        this.currentWeatherId = currentWeatherId;
        this.locationId = locationId;
        this.temperature = temperature;
        this.rain = rain;
        this.snow = snow;
        this.clouds = clouds;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.degrees = degrees;
        this.dateOfUpdate = dateOfUpdate;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    @Generated(hash = 1937976266)
    public CurrentWeatherEntity() {
    }

    public long getCurrentWeatherId() {
        return currentWeatherId;
    }

    public void setCurrentWeatherId(long currentWeatherId) {
        this.currentWeatherId = currentWeatherId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
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

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public int getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(int dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1448503762)
    public List<WeatherEntity> getWeathers() {
        if (weathers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WeatherEntityDao targetDao = daoSession.getWeatherEntityDao();
            List<WeatherEntity> weathersNew = targetDao
                    ._queryCurrentWeatherEntity_Weathers(currentWeatherId);
            synchronized (this) {
                if (weathers == null) {
                    weathers = weathersNew;
                }
            }
        }
        return weathers;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 883555496)
    public synchronized void resetWeathers() {
        weathers = null;
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1568857282)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCurrentWeatherEntityDao() : null;
    }
}