package by.reshetnikov.proweather.data.db;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntityDao;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntityDao;
import by.reshetnikov.proweather.exception.NoSavedForecastDataException;
import by.reshetnikov.proweather.exception.NoSavedLocationException;
import by.reshetnikov.proweather.utils.CalendarUtil;
import io.reactivex.Completable;
import io.reactivex.Single;


public class AppDbData implements DbContract {

    private static final String TAG = AppDbData.class.getSimpleName();

    private final NowForecastEntityDao nowForecastEntityDao;
    private final HoursForecastEntityDao hourlyForecastDao;
    private final LocationEntityDao locationDao;

    @Inject
    public AppDbData(DaoSession daoSession) {
        nowForecastEntityDao = daoSession.getNowForecastEntityDao();
        hourlyForecastDao = daoSession.getHoursForecastEntityDao();
        locationDao = daoSession.getLocationEntityDao();
    }

    @Override
    public Single<NowForecastEntity> getSavedNowForecast(@NonNull final LocationEntity location) {
        if (getCurrentForecastEntity(location.getLocationId()) == null)
            Single.error(new NoSavedForecastDataException());

        return Single.fromCallable(new Callable<NowForecastEntity>() {
            @Override
            public NowForecastEntity call() throws Exception {
                return getCurrentForecastEntity(location.getLocationId());
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
            return Single.error(new NoSavedLocationException());

        return Single.fromCallable(new Callable<LocationEntity>() {
            @Override
            public LocationEntity call() throws Exception {
                return getChosenLocationEntity();
            }
        });
    }

    @Override
    public Single<List<LocationEntity>> getSavedLocations() {
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
    public Completable removeLocation(final LocationEntity locationEntity) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return removeLocationEntity(locationEntity);
            }
        });
    }

    private NowForecastEntity getCurrentForecastEntity(int locationId) {
        return nowForecastEntityDao.queryBuilder()
                .where(NowForecastEntityDao.Properties.LocationId.eq(locationId))
                .orderDesc(NowForecastEntityDao.Properties.DateOfUpdate)
                .limit(1)
                .build()
                .unique();
    }

    private Boolean saveCurrentForecastEntity(NowForecastEntity currentWeather) {
        nowForecastEntityDao.save(currentWeather);
        return true;
    }

    private List<HoursForecastEntity> getHourlyForecastWeatherEntity(int locationId) {
        return hourlyForecastDao.queryBuilder()
                .where(HoursForecastEntityDao.Properties.LocationId.eq(locationId))
                .where(HoursForecastEntityDao.Properties.Date.ge(Integer.valueOf(CalendarUtil.getTodayDate())))
                .build()
                .list();
    }

    private Boolean saveHourlyForecastWeatherEntity(List<HoursForecastEntity> forecastEntities) {
        for (HoursForecastEntity entity : forecastEntities)
            hourlyForecastDao.insertOrReplace(entity);
        return true;
    }

    private LocationEntity getSavedLocationEntity(long id) {
        return locationDao.load(id);
    }

    private LocationEntity getChosenLocationEntity() {
        LocationEntity defaultLocation = locationDao.queryBuilder()
                .where(LocationEntityDao.Properties.IsCurrent.eq(true))
                .build()
                .unique();
        if (defaultLocation == null)
            return locationDao.queryBuilder()
                    .where(LocationEntityDao.Properties.Position.eq(0))
                    .build()
                    .unique();

        return defaultLocation;
    }

    private List<LocationEntity> getSavedLocationEntities() {
        List<LocationEntity> list = locationDao.queryBuilder()
                .orderAsc(LocationEntityDao.Properties.Position)
                .list();
        return list;
    }

    private Boolean updateLocationEntity(LocationEntity locationEntity) {
        locationDao.insertOrReplace(locationEntity);
        return Boolean.TRUE;
    }

    private Boolean saveNewLocationEntity(LocationEntity locationEntity) {
        LocationEntity latestEntity = locationDao.queryBuilder()
                .distinct()
                .orderDesc(LocationEntityDao.Properties.Position)
                .limit(1)
                .build().unique();

        setOrderPosition(locationEntity, latestEntity);

        locationDao.save(locationEntity);
        return true;
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
