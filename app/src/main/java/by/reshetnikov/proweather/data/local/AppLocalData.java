package by.reshetnikov.proweather.data.local;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.contract.LocalDataContract;
import by.reshetnikov.proweather.data.AppDataModelsBuilder;
import by.reshetnikov.proweather.data.local.preferences.SharedPreferencesStorage;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.model.dbmodels.DaoSession;
import by.reshetnikov.proweather.model.dbmodels.ForecastEntity;
import by.reshetnikov.proweather.model.dbmodels.LocationEntity;
import by.reshetnikov.proweather.model.dbmodels.LocationEntityDao;
import by.reshetnikov.proweather.model.dbmodels.WeatherEntity;
import by.reshetnikov.proweather.model.dbmodels.WeatherEntityDao;
import io.reactivex.Observable;
import io.reactivex.Single;


public class AppLocalData implements LocalDataContract {

    private DaoSession daoSession;
    private Context context;
    private SharedPreferencesStorage preferences;
    private FusedLocationProviderClient locationClient;

    public AppLocalData(Context context, DaoSession daoSession) {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
        this.context = context;
        this.daoSession = daoSession;
        preferences = SharedPreferencesStorage.getInstance(context);
        locationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public Observable<WeatherEntity> getCurrentWeather(String locationId) {
        WeatherEntity weatherEntity = getCurrentWeatherEntity(locationId);
        if (weatherEntity == null)
            return null;
        return Observable.just(weatherEntity);
    }

    @Override
    public Observable<ForecastEntity> getForecastWeather(String locationId) {
        return null;
    }

    @Override
    public Location getCurrentLocation() {
        if (preferences.getCanUseCurrentLocationPreference()) {
            return preferences.getCurrentLocationPreference();
        }
        return null;
    }

    @Override
    public LocationEntity getChosenLocation() {
        LocationEntityDao locationDao = daoSession.getLocationEntityDao();
        LocationEntity locationEntity = locationDao.queryBuilder()
                .where(LocationEntityDao.Properties.IsCurrent.eq(true))
                .build()
                .unique();
        return locationEntity;
    }

    @Override
    public Single<List<LocationEntity>> getLocations() {
        LocationEntityDao locationDao = daoSession.getLocationEntityDao();
        List<LocationEntity> locationEntities = locationDao.queryBuilder()
                .orderAsc(LocationEntityDao.Properties.Position)
                .build().list();
        return Single.just(locationEntities);
    }

    private WeatherEntity getCurrentWeatherEntity(String locationId) {
        WeatherEntityDao weatherDao = daoSession.getWeatherEntityDao();
        return weatherDao.queryBuilder()
                .where(WeatherEntityDao.Properties.LocationId.eq(locationId))
                .orderAsc(WeatherEntityDao.Properties.Date)
                .limit(1)
                .build().unique();
    }

    @Override
    public void saveLocation(LocationAppModel locationModel) {
        LocationEntityDao locationDao = daoSession.getLocationEntityDao();
        LocationEntity locationEntity = AppDataModelsBuilder.createLocationEntity(locationModel);
        LocationEntity dbEntity = locationDao.queryBuilder()
                .where(LocationEntityDao.Properties.LocationId.eq(locationModel.getLocationId()))
                .build().unique();
        if (dbEntity != null) {
            locationEntity.setId(dbEntity.getId());
        }
        locationDao.save(locationEntity);
    }

    @Override
    public void saveCurrentWeather(WeatherEntity currentWeather) {

    }

    @Override
    public void saveForecastWeather(ForecastEntity forecastWeather) {

    }

    @Override
    public boolean canUseCurrentLocation() {
        return preferences.getCanUseCurrentLocationPreference();
    }


    @Override
    public Single<UnitsAppModel> getUnits() {
        return preferences.getChosenUnits();
    }

}
