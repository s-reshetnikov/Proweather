package by.reshetnikov.proweather.data.local;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.local.dbmodels.entities.CityEntityDao;
import by.reshetnikov.proweather.data.local.dbmodels.entities.DaoSession;
import by.reshetnikov.proweather.data.local.dbmodels.entities.WeatherEntityDao;
import by.reshetnikov.proweather.data.local.preferences.SharedPreferencesStorage;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.model.dbmodels.CityEntity;
import by.reshetnikov.proweather.model.dbmodels.ForecastEntity;
import by.reshetnikov.proweather.model.dbmodels.WeatherEntity;
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
    public Observable<WeatherEntity> getCurrentWeather(String cityId) {
        WeatherEntity weatherEntity = getCurrentWeatherEntity(cityId);
        if (weatherEntity == null)
            return null;
        return Observable.just(weatherEntity);
    }

    @Override
    public Observable<ForecastEntity> getForecastWeather(String cityId) {
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
    public CityEntity getChosenCity() {
        CityEntityDao cityDao = daoSession.getCityEntityDao();
        CityEntity cityEntity = cityDao.queryBuilder()
                .where(CityEntityDao.Properties.IsCurrent.eq(true))
                .build()
                .unique();
        return cityEntity;
    }

    @Override
    public Observable<List<CityEntity>> getCities() {
        return null;
    }

    private WeatherEntity getCurrentWeatherEntity(String cityId) {
        WeatherEntityDao weatherDao = daoSession.getWeatherEntityDao();
        return weatherDao.queryBuilder()
                .where(WeatherEntityDao.Properties.CityId.eq(cityId))
                .orderAsc(WeatherEntityDao.Properties.Date)
                .limit(1)
                .build().unique();
    }

    @Override
    public void saveCity(CityEntity city) {
        CityEntityDao cityDao = daoSession.getCityEntityDao();
        cityDao.save(city);
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
