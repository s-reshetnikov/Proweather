package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

@Entity(active = true)
public class CurrentForecastEntity {

    @Id
    private long currentWeatherId;
    private String locationId;
    @ToOne(joinProperty = "currentWeatherId")
    private WeatherEntity weatherEntity;
    private double temperature;
    private double rain;
    private double snow;
    private int humidity;
    private double windSpeed;
    private int windDirectionDegrees;
    private int dateOfUpdate;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 799483761)
    private transient CurrentForecastEntityDao myDao;

    @Generated(hash = 252242787)
    public CurrentForecastEntity(long currentWeatherId, String locationId,
                                 double temperature, double rain, double snow, int humidity,
                                 double windSpeed, int windDirectionDegrees, int dateOfUpdate) {
        this.currentWeatherId = currentWeatherId;
        this.locationId = locationId;
        this.temperature = temperature;
        this.rain = rain;
        this.snow = snow;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirectionDegrees = windDirectionDegrees;
        this.dateOfUpdate = dateOfUpdate;
    }

    @Generated(hash = 417053749)
    public CurrentForecastEntity() {
    }

    @Generated(hash = 1942152839)
    private transient Long weatherEntity__resolvedKey;

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

    public int getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(int dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public int getHumidity() {
        return this.humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1799296048)
    public WeatherEntity getWeatherEntity() {
        long __key = this.currentWeatherId;
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
    @Generated(hash = 1489315537)
    public void setWeatherEntity(@NotNull WeatherEntity weatherEntity) {
        if (weatherEntity == null) {
            throw new DaoException(
                    "To-one property 'currentWeatherId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.weatherEntity = weatherEntity;
            currentWeatherId = weatherEntity.getWeatherId();
            weatherEntity__resolvedKey = currentWeatherId;
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1770972121)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCurrentForecastEntityDao() : null;
    }
}