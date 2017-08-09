package by.reshetnikov.proweather.data.db;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.db.model.CurrentForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntityDao;
import by.reshetnikov.proweather.data.db.model.WeatherEntityDao;
import by.reshetnikov.proweather.data.model.location.LocationContract;
import io.reactivex.Completable;
import io.reactivex.Single;


public class AppDbData implements DbContract {

    private static final String TAG = AppDbData.class.getSimpleName();

    private final CurrentForecastEntityDao currentForecastDao;
    private final HourlyForecastEntityDao hourlyForecastDao;
    private final DailyForecastEntityDao dailyForecastDao;
    private final LocationEntityDao locationDao;

    @Inject
    public AppDbData(DaoSession daoSession) {
        currentForecastDao = daoSession.getCurrentForecastEntityDao();
        hourlyForecastDao = daoSession.getHourlyForecastEntityDao();
        dailyForecastDao = daoSession.getDailyForecastEntityDao();
        locationDao = daoSession.getLocationEntityDao();
    }

    @Override
    public Single<CurrentForecastEntity> getSavedCurrentWeather(final LocationContract location) {
        return Single.fromCallable(new Callable<CurrentForecastEntity>() {
            @Override
            public CurrentForecastEntity call() throws Exception {
                return getCurrentWeatherEntity(location.getLocationId());
            }
        });
    }

    @Override
    public Completable saveCurrentWeather(final CurrentForecastEntity currentWeather) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveCurrentWeatherEntity(currentWeather);
            }
        });
    }

    @Override
    public Single<HourlyForecastEntity> getSavedHourlyForecastWeather(final LocationContract location) {
        return Single.fromCallable(new Callable<HourlyForecastEntity>() {
            @Override
            public HourlyForecastEntity call() throws Exception {
                return getHourlyForecastWeatherEntity(location.getLocationId());
            }
        });
    }

    @Override
    public Completable saveHourlyForecastWeather(final HourlyForecastEntity forecastWeather) {
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveHourlyForecastWeatherEntity(forecastWeather);
            }
        });
    }

    @Override
    public Single<DailyForecastEntity> getSavedDailyForecastWeather(final LocationContract location) {
        return Single.fromCallable(new Callable<DailyForecastEntity>() {
            @Override
            public DailyForecastEntity call() throws Exception {
                return getDailyForecastWeatherEntity(location.getLocationId());
            }
        });
    }

    @Override
    public Completable saveDailyForecastWeather(final DailyForecastEntity dailyForecast) {
        return Completable.fromCallable(new Callable() {
            @Override
            public Boolean call() throws Exception {
                return saveDailyForecastWeatherEntity(dailyForecast);
            }
        });
    }

    @Override
    public Single<LocationEntity> getChosenLocation() {
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

    private CurrentForecastEntity getCurrentWeatherEntity(int locationId) {
        return currentForecastDao.queryBuilder()
                .where(WeatherEntityDao.Properties.LocationId.eq(locationId))
                .orderAsc(WeatherEntityDao.Properties.Date)
                .limit(1)
                .build()
                .unique();
    }

    private Boolean saveCurrentWeatherEntity(CurrentForecastEntity currentWeather) {
        currentForecastDao.save(currentWeather);
        return true;
    }

    private HourlyForecastEntity getHourlyForecastWeatherEntity(int locationId) {
        return hourlyForecastDao.queryBuilder()
                .where(HourlyForecastEntityDao.Properties.LocationId.eq(locationId))
                .build()
                .unique();
    }


    private Boolean saveHourlyForecastWeatherEntity(HourlyForecastEntity hourlyForecastEntity) {
        hourlyForecastDao.save(hourlyForecastEntity);
        return true;
    }

    private DailyForecastEntity getDailyForecastWeatherEntity(int locationId) {
        return dailyForecastDao.queryBuilder()
                .where(DailyForecastEntityDao.Properties.LocationId.eq(locationId))
                .build()
                .unique();
    }

    private Boolean saveDailyForecastWeatherEntity(DailyForecastEntity dailyForecastEntity) {
        dailyForecastDao.insertOrReplace(dailyForecastEntity);
        return Boolean.TRUE;
    }

    private LocationEntity getChosenLocationEntity() {
        return locationDao.queryBuilder()
                .where(LocationEntityDao.Properties.IsCurrent.eq(true))
                .build().unique();
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
