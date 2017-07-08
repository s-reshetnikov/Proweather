//package by.reshetnikov.proweather.data.local;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import by.reshetnikov.proweather.ProWeatherApp;
//import AppSharedPreferencesData;
//import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
//import by.reshetnikov.proweather.model.dbmodels.DaoSession;
//import ForecastEntity;
//import LocationEntity;
//import by.reshetnikov.proweather.model.dbmodels.LocationEntityDao;
//import WeatherEntity;
//import by.reshetnikov.proweather.model.dbmodels.WeatherEntityDao;
//import io.reactivex.Observable;
//
//@Singleton
//public class AppLocalData implements LocalDataContract {
//
//    private DaoSession daoSession;
//    private AppSharedPreferencesData preferences;
//
//    @Inject
//    public AppLocalData(DaoSession daoSession) {
//        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
//        this.daoSession = daoSession;
//        preferences = AppSharedPreferencesData.getInstance(ProWeatherApp.getAppContext());
//    }
//
//    @Override
//    public Observable<WeatherEntity> getCurrentWeather(String locationId) {
//        WeatherEntity weatherEntity = getCurrentWeatherEntity(locationId);
//        if (weatherEntity == null)
//            return null;
//        return Observable.just(weatherEntity);
//    }
//
//    @Override
//    public Observable<ForecastEntity> getForecastWeather(String locationId) {
//        return null;
//    }
//
//
//    @Override
//    public Observable<LocationEntity> getChosenLocation() {
//        LocationEntityDao locationDao = daoSession.getLocationEntityDao();
////        LocationEntity locationEntity = locationDao.queryBuilder()
////                .where(LocationEntityDao.Properties.IsCurrent.eq(true))
////                .build()
////                .unique();
////        return locationEntity;
//        return null;
//    }
//
//    @Override
//    public Observable<List<LocationEntity>> getSavedLocations() {
//        LocationEntityDao locationDao = daoSession.getLocationEntityDao();
////        return locationDao.queryBuilder()
////                .orderAsc(LocationEntityDao.Properties.Position)
////                .build().list();
//        return null;
//    }
//
//    private WeatherEntity getCurrentWeatherEntity(String locationId) {
//        WeatherEntityDao weatherDao = daoSession.getWeatherEntityDao();
//        return weatherDao.queryBuilder()
//                .where(WeatherEntityDao.Properties.LocationId.eq(locationId))
//                .orderAsc(WeatherEntityDao.Properties.Date)
//                .limit(1)
//                .build().unique();
//    }
//
//    @Override
//    public Observable<Boolean> saveNewLocation(LocationEntity locationEntity) {
//        daoSession.getLocationEntityDao().save(locationEntity);
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> saveNewLocations(List<LocationEntity> locationEntities) {
//        for (LocationEntity entity : locationEntities) {
//            daoSession.getLocationEntityDao().save(entity);
//        }
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> updateLocation(LocationEntity locationEntity) {
//        daoSession.getLocationEntityDao().update(locationEntity);
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> updateLocations(List<LocationEntity> locationEntities) {
//        for (LocationEntity entity : locationEntities) {
//            daoSession.getLocationEntityDao().update(entity);
//        }
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> saveCurrentWeather(WeatherEntity currentWeather) {
//
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> saveForecastWeather(ForecastEntity forecastWeather) {
//
//        return null;
//    }
//
//
//    @Override
//    public Observable<Boolean> removeLocation(LocationEntity locationEntity) {
//        daoSession.getLocationEntityDao().delete(locationEntity);
//        return null;
//    }
//
//
//    @Override
//    public UnitsAppModel getUnits() {
//        return preferences.getUnits();
//    }
//
//}
