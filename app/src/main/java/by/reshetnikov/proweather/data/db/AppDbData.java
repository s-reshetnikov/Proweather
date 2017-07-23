package by.reshetnikov.proweather.data.db;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.CurrentWeatherEntity;
import by.reshetnikov.proweather.data.db.model.CurrentWeatherEntityDao;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.DaoMaster;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntityDao;
import by.reshetnikov.proweather.data.db.model.WeatherEntityDao;
import io.reactivex.Observable;


public class AppDbData implements DbContract {

    private static final String TAG = AppDbData.class.getSimpleName();

    private final CurrentWeatherEntityDao currentWeatherDao;
    private final HourlyForecastEntityDao hourlyForecastDao;
    private final DailyForecastEntityDao dailyForecastDao;
    private final LocationEntityDao locationDao;

    @Inject
    public AppDbData(DaoSession daoSession) {
        currentWeatherDao = daoSession.getCurrentWeatherEntityDao();
        hourlyForecastDao = daoSession.getHourlyForecastEntityDao();
        dailyForecastDao = daoSession.getDailyForecastEntityDao();
        locationDao = daoSession.getLocationEntityDao();
    }

    @Override
    public Observable<CurrentWeatherEntity> getCurrentWeather(final String locationId) {
        return Observable.fromCallable(new Callable<CurrentWeatherEntity>() {
            @Override
            public CurrentWeatherEntity call() throws Exception {
                return getCurrentWeatherEntity(locationId);
            }
        });
    }

    @Override
    public Observable<Boolean> saveCurrentWeather(final CurrentWeatherEntity currentWeather) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveCurrentWeatherEntity(currentWeather);
            }
        });
    }

    @Override
    public Observable<HourlyForecastEntity> getHourlyForecastWeather(final String locationId) {
        return Observable.fromCallable(new Callable<HourlyForecastEntity>() {
            @Override
            public HourlyForecastEntity call() throws Exception {
                return getHourlyForecastWeatherEntity(locationId);
            }
        });
    }

    @Override
    public Observable<Boolean> saveHourlyForecastWeather(final HourlyForecastEntity forecastWeather) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveHourlyForecastWeatherEntity(forecastWeather);
            }
        });
    }

    @Override
    public Observable<DailyForecastEntity> getDailyForecastWeather(final String locationId) {
        return Observable.fromCallable(new Callable<DailyForecastEntity>() {
            @Override
            public DailyForecastEntity call() throws Exception {
                return getDailyForecastWeatherEntity(locationId);
            }
        });
    }

    @Override
    public Observable<LocationEntity> getChosenLocation() {
        return Observable.fromCallable(new Callable<LocationEntity>() {
            @Override
            public LocationEntity call() throws Exception {
                return getChosenLocationEntity();
            }
        });
    }

    @Override
    public Observable<List<LocationEntity>> getSavedLocations() {
        return Observable.fromCallable(new Callable<List<LocationEntity>>() {
            @Override
            public List<LocationEntity> call() {
                return getSavedLocationEntities();
            }
        });
    }

    @Override
    public Observable<Boolean> saveNewLocation(final LocationEntity locationEntity) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveNewLocationEntity(locationEntity);
            }
        });
    }

    @Override
    public Observable<Boolean> updateLocation(final LocationEntity locationEntity) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return updateLocationEntity(locationEntity);
            }
        });
    }

    @Override
    public Observable<Boolean> removeLocation(final LocationEntity locationEntity) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return removeLocationEntity(locationEntity);
            }
        });
    }

    private CurrentWeatherEntity getCurrentWeatherEntity(String locationId) {
        return currentWeatherDao.queryBuilder()
                .where(WeatherEntityDao.Properties.LocationId.eq(locationId))
                .orderAsc(WeatherEntityDao.Properties.Date)
                .limit(1)
                .build()
                .unique();
    }

    private Boolean saveCurrentWeatherEntity(CurrentWeatherEntity currentWeather) {
        currentWeatherDao.save(currentWeather);
        return true;
    }

    private HourlyForecastEntity getHourlyForecastWeatherEntity(String locationId) {
        return hourlyForecastDao.queryBuilder()
                .where(HourlyForecastEntityDao.Properties.LocationId.eq(locationId))
                .build().unique();
    }


    private Boolean saveHourlyForecastWeatherEntity(HourlyForecastEntity hourlyForecastEntity) {
        hourlyForecastDao.save(hourlyForecastEntity);
        return true;
    }

    private DailyForecastEntity getDailyForecastWeatherEntity(String locationId) {
        return dailyForecastDao.queryBuilder()
                .where(DailyForecastEntityDao.Properties.LocationId.eq(locationId))
                .build().unique();
    }

    private long saveDailyForecastWeatherEntity(DailyForecastEntity dailyForecastEntity) {
        return dailyForecastDao.insertOrReplace(dailyForecastEntity);
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
