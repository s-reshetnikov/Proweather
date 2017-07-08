package by.reshetnikov.proweather.data.db;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.DaoMaster;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntityDao;
import by.reshetnikov.proweather.data.db.model.WeatherEntity;
import by.reshetnikov.proweather.data.db.model.WeatherEntityDao;
import io.reactivex.Observable;


public class AppDbData implements DbContract {

    private final DaoSession daoSession;
    private final WeatherEntityDao weatherDao;
    private final DailyForecastEntityDao forecastDao;
    private final LocationEntityDao locationDao;

    @Inject
    public AppDbData(AppDbOpenHelper dbHelper) {
        daoSession = new DaoMaster(dbHelper.getWritableDb()).newSession();
        weatherDao = daoSession.getWeatherEntityDao();
        forecastDao = daoSession.getDailyForecastEntityDao();
        locationDao = daoSession.getLocationEntityDao();
    }

    @Override
    public Observable<WeatherEntity> getCurrentWeather(final String locationId) {
        return Observable.fromCallable(new Callable<WeatherEntity>() {
            @Override
            public WeatherEntity call() throws Exception {
                return getCurrentWeatherEntity(locationId);
            }
        });
    }

    @Override
    public Observable<Boolean> saveCurrentWeather(final WeatherEntity currentWeather) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveCurrentWeatherEntity(currentWeather);
            }
        });
    }

    @Override
    public Observable<DailyForecastEntity> getForecastWeather(final String locationId) {
        return Observable.fromCallable(new Callable<DailyForecastEntity>() {
            @Override
            public DailyForecastEntity call() throws Exception {
                return getForecastWeatherEntity(locationId);
            }
        });
    }

    @Override
    public Observable<Boolean> saveForecastWeather(final DailyForecastEntity forecastWeather) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveForecastWeatherEntity(forecastWeather);
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
            public List<LocationEntity> call() throws Exception {
                return getSavedLocationEntities();
            }
        });
    }

    @Override
    public Observable<Boolean> saveNewLocation(final LocationEntity locationEntity) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveOrUpdateLocationEntity(locationEntity);
            }
        });
    }

    @Override
    public Observable<Boolean> updateLocation(final LocationEntity locationEntity) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return saveOrUpdateLocationEntity(locationEntity);
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

    private WeatherEntity getCurrentWeatherEntity(String locationId) {
        return weatherDao.queryBuilder()
                .where(WeatherEntityDao.Properties.LocationId.eq(locationId))
                .orderAsc(WeatherEntityDao.Properties.Date)
                .limit(1)
                .build()
                .unique();
    }

    private Boolean saveCurrentWeatherEntity(WeatherEntity currentWeather) {
        weatherDao.save(currentWeather);
        return true;
    }

    private DailyForecastEntity getForecastWeatherEntity(String locationId) {
        return forecastDao.queryBuilder()
                .where(DailyForecastEntityDao.Properties.LocationId.eq(locationId))
                .build().unique();
    }

    private Boolean saveForecastWeatherEntity(DailyForecastEntity forecastWeather) {
        forecastDao.save(forecastWeather);
        return true;
    }

    private LocationEntity getChosenLocationEntity() {
        return locationDao.queryBuilder()
                .where(LocationEntityDao.Properties.IsCurrent.eq(true))
                .build().unique();
    }

    private List<LocationEntity> getSavedLocationEntities() {
        return locationDao.loadAll();
    }

    private Boolean saveOrUpdateLocationEntity(LocationEntity locationEntity) {
        locationDao.save(locationEntity);
        return true;
    }

    private Boolean removeLocationEntity(LocationEntity locationEntity) {
        locationDao.delete(locationEntity);
        return true;
    }
}
