package by.reshetnikov.proweather.data.db;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntityDao;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntityDao;
import by.reshetnikov.proweather.data.exception.NoLocationException;
import by.reshetnikov.proweather.data.exception.NoSavedForecastDataException;
import by.reshetnikov.proweather.utils.CalendarUtil;
import io.reactivex.Completable;
import io.reactivex.Single;
import timber.log.Timber;


public class AppDbData implements DbContract {

    private final NowForecastEntityDao nowForecastEntityDao;
    private final HoursForecastEntityDao hourlyForecastDao;
    private final DailyForecastEntityDao dailyForecastEntityDao;
    private final LocationEntityDao locationDao;

    @Inject
    public AppDbData(DaoSession daoSession) {
        nowForecastEntityDao = daoSession.getNowForecastEntityDao();
        hourlyForecastDao = daoSession.getHoursForecastEntityDao();
        dailyForecastEntityDao = daoSession.getDailyForecastEntityDao();
        locationDao = daoSession.getLocationEntityDao();
    }

    @Override
    public Single<NowForecastEntity> getSavedNowForecast(@NonNull final LocationEntity location) {
        if (getCurrentForecastEntity(location.getLocationId()) == null)
            Single.error(new NoSavedForecastDataException());

        return Single.fromCallable(new Callable<NowForecastEntity>() {
            @Override
            public NowForecastEntity call() throws Exception {
                NowForecastEntity nowForecast = getCurrentForecastEntity(location.getLocationId());
                return nowForecast;
            }
        });
    }

    @Override
    public Completable saveCurrentWeather(final NowForecastEntity currentWeather) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveCurrentForecastEntity(currentWeather);
            }
        });
    }

    @Override
    public Single<List<HoursForecastEntity>> getSavedHourlyForecast(final LocationEntity location) {
        return Single.fromCallable(new Callable<List<HoursForecastEntity>>() {
            @Override
            public List<HoursForecastEntity> call() throws Exception {
                return getHourlyForecastWeatherEntity(location.getLocationId());
            }
        });
    }

    @Override
    public Completable saveHourlyForecasts(final List<HoursForecastEntity> forecastWeather) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveHourlyForecastWeatherEntity(forecastWeather);
            }
        });
    }

    @Override
    public Single<List<DailyForecastEntity>> getSavedDailyForecast(final LocationEntity location) {
        return Single.fromCallable(new Callable<List<DailyForecastEntity>>() {
            @Override
            public List<DailyForecastEntity> call() throws Exception {
                return getDailyForecastWeatherEntity(location.getLocationId());
            }
        });
    }

    @Override
    public Completable saveDailyForecasts(final List<DailyForecastEntity> forecastWeather) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveDailyForecastWeatherEntity(forecastWeather);
            }
        });
    }

    @Override
    public Single<LocationEntity> getSavedLocation(final long id) {
        return Single.fromCallable(new Callable<LocationEntity>() {
            @Override
            public LocationEntity call() throws Exception {
                return getSavedLocationEntity(id);
            }
        });
    }


    @Override
    public Single<LocationEntity> getChosenLocation() {
        if (getSavedLocationEntities().size() == 0)
            return Single.error(new NoLocationException());

        return Single.fromCallable(new Callable<LocationEntity>() {
            @Override
            public LocationEntity call() throws Exception {
                return getChosenLocationEntity();
            }
        });
    }

    @Override
    public Single<List<LocationEntity>> getSavedLocations() {
        if (getSavedLocationEntities().size() == 0)
            return Single.error(new NoLocationException());

        return Single.fromCallable(new Callable<List<LocationEntity>>() {
            @Override
            public List<LocationEntity> call() {
                return getSavedLocationEntities();
            }
        });
    }

    @Override
    public Completable saveNewLocation(final LocationEntity locationEntity) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveNewLocationEntity(locationEntity);
            }
        });
    }

    @Override
    public Completable updateLocation(final LocationEntity locationEntity) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return updateLocationEntity(locationEntity);
            }
        });
    }

    @Override
    public Completable updateLocations(final List<LocationEntity> locations) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return updateLocationEntities(locations);
            }
        });
    }

    @Override
    public Completable removeLocation(final LocationEntity locationEntity) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return removeLocationEntity(locationEntity);
            }
        });
    }

    @Override
    public Completable saveOrUpdateLocation(final LocationEntity location) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveOrUpdateLocationEntity(location);
            }
        });
    }

    private NowForecastEntity getCurrentForecastEntity(int locationId) {
        return nowForecastEntityDao.queryBuilder()
                .where(NowForecastEntityDao.Properties.LocationId.eq(locationId))
                .orderDesc(NowForecastEntityDao.Properties.DateOfUpdate)
                .limit(1)
                .unique();
    }

    private Boolean saveCurrentForecastEntity(NowForecastEntity currentWeather) {
        nowForecastEntityDao.save(currentWeather);
        return true;
    }

    private List<HoursForecastEntity> getHourlyForecastWeatherEntity(int locationId) {
        return hourlyForecastDao.queryBuilder()
                .where(HoursForecastEntityDao.Properties.LocationId.eq(locationId))
                .where(HoursForecastEntityDao.Properties.Date.ge(CalendarUtil.getTodayDate()))
                .build()
                .list();
    }

    private Boolean saveHourlyForecastWeatherEntity(List<HoursForecastEntity> forecastEntities) {
        for (HoursForecastEntity entity : forecastEntities)
            hourlyForecastDao.insertOrReplace(entity);
        return true;
    }

    private List<DailyForecastEntity> getDailyForecastWeatherEntity(int locationId) {
        return dailyForecastEntityDao.queryBuilder()
                .where(HoursForecastEntityDao.Properties.LocationId.eq(locationId))
                .where(HoursForecastEntityDao.Properties.Date.ge(CalendarUtil.getTodayDate()))
                .build()
                .list();
    }

    private Boolean saveDailyForecastWeatherEntity(List<DailyForecastEntity> forecastEntities) {
        for (DailyForecastEntity entity : forecastEntities)
            dailyForecastEntityDao.insertOrReplace(entity);
        return true;
    }

    private LocationEntity getSavedLocationEntity(long id) {
        return locationDao.load(id);
    }

    private LocationEntity getChosenLocationEntity() {
        int firstPosition = 0;
        LocationEntity defaultLocation = locationDao.queryBuilder()
                .where(LocationEntityDao.Properties.Position.eq(firstPosition))
                .unique();
        if (defaultLocation == null)
            Timber.w("default location is not set");
        return defaultLocation;
    }

    private List<LocationEntity> getSavedLocationEntities() {
        List<LocationEntity> list = locationDao.queryBuilder()
                .orderAsc(LocationEntityDao.Properties.Position)
                .list();
        return list;
    }

    private Boolean saveOrUpdateLocationEntity(LocationEntity location) {
        LocationEntity savedLocation = locationDao.queryBuilder()
                .where(LocationEntityDao.Properties.LocationId.eq(location.getLocationId()))
                .unique();

        if (savedLocation != null) {
            location.setPosition(savedLocation.getPosition());
            return updateLocationEntity(location);
        }

        return saveNewLocationEntity(location);
    }

    private Boolean updateLocationEntity(LocationEntity location) {
        locationDao.insertOrReplace(location);
        return Boolean.TRUE;
    }

    private Boolean updateLocationEntities(List<LocationEntity> locations) {
        locationDao.updateInTx(locations);
        return Boolean.TRUE;
    }

    private Boolean saveNewLocationEntity(LocationEntity locationEntity) {
        LocationEntity lastLocation = getLastLocation();
        setOrderPosition(locationEntity, lastLocation);

        locationDao.save(locationEntity);
        return true;
    }

    private LocationEntity getLastLocation() {
        return locationDao.queryBuilder()
                .distinct()
                .orderDesc(LocationEntityDao.Properties.Position)
                .limit(1)
                .unique();
    }

    private void setOrderPosition(LocationEntity locationEntity, LocationEntity latestEntity) {
        if (latestEntity != null) {
            int lastPosition = latestEntity.getPosition() + 1;
            locationEntity.setPosition(lastPosition);
        } else {
            int firstPosition = 0;
            locationEntity.setPosition(firstPosition);
        }
    }

    private Boolean removeLocationEntity(LocationEntity locationEntity) {
        locationDao.delete(locationEntity);
        return true;
    }
}