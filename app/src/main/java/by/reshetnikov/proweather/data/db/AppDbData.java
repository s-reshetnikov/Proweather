package by.reshetnikov.proweather.data.db;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.DaoMaster;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.db.model.ForecastEntity;
import by.reshetnikov.proweather.data.db.model.ForecastEntityDao;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.WeatherEntity;
import by.reshetnikov.proweather.data.db.model.WeatherEntityDao;
import io.reactivex.Observable;


public class AppDbData implements DbContract {

    private final DaoSession daoSession;

    @Inject
    AppDbData(AppDbOpenHelper dbHelper) {
        daoSession = new DaoMaster(dbHelper.getWritableDb()).newSession();
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
    public Observable<ForecastEntity> getForecastWeather(final String locationId) {
        return Observable.fromCallable(new Callable<ForecastEntity>() {
            @Override
            public ForecastEntity call() throws Exception {
                return getForecastWeatherEntity(locationId);
            }
        });
    }

    @Override
    public Observable<Boolean> saveForecastWeather(final ForecastEntity forecastWeather) {
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

    private WeatherEntity getCurrentWeatherEntity(String locationId) {
        WeatherEntityDao weatherDao = daoSession.getWeatherEntityDao();
        return weatherDao.queryBuilder()
                .where(WeatherEntityDao.Properties.LocationId.eq(locationId))
                .orderAsc(WeatherEntityDao.Properties.Date)
                .limit(1)
                .build()
                .unique();
    }

    private Boolean saveCurrentWeatherEntity(WeatherEntity currentWeather) {
        WeatherEntityDao weatherDao = daoSession.getWeatherEntityDao();
        weatherDao.save(currentWeather);
        return true;
    }

    private ForecastEntity getForecastWeatherEntity(String locationId) {
        ForecastEntityDao forecastDao = daoSession.getForecastEntityDao();
        return forecastDao.queryBuilder()
                .where(ForecastEntityDao.Properties.LocationId.eq(locationId))
                .build().unique();
    }

    private Boolean saveForecastWeatherEntity(ForecastEntity forecastWeather) {
        return null;
    }

    private LocationEntity getChosenLocationEntity() {
        return null;
    }

    private List<LocationEntity> getSavedLocationEntities() {
        return null;
    }

    private Boolean saveNewLocationEntity(LocationEntity locationEntity) {
        return null;
    }

    private Boolean updateLocationEntity(LocationEntity locationEntity) {
        return null;
    }

    private Boolean removeLocationEntity(LocationEntity locationEntity) {
        return null;
    }
}
