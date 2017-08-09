package by.reshetnikov.proweather.data;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.db.DbContract;
import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.model.AppModelFactory;
import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.data.model.unit.UnitsContract;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapter;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapterContract;
import by.reshetnikov.proweather.data.model.weather.daily.DailyForecastAdapterContract;
import by.reshetnikov.proweather.data.model.weather.hourly.HourlyForecastAdapterContract;
import by.reshetnikov.proweather.data.network.ForecastSource;
import by.reshetnikov.proweather.data.network.WeatherApiDataContract;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import by.reshetnikov.proweather.utils.NetworkUtils;
import by.reshetnikov.proweather.utils.scheduler.AppSchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DataRepository implements DataContract {

    private static final String TAG = DataRepository.class.getSimpleName();

    private DbContract dbData;
    private WeatherApiDataContract apiData;
    private AppSharedPreferencesData sharedPreferencesData;
    private AppSchedulerProvider scheduler;

    @Inject
    public DataRepository(DbContract dbData,
                          WeatherApiDataContract apiData,
                          AppSharedPreferencesData sharedPreferencesData
    ) {
        this.dbData = dbData;
        this.apiData = apiData;
        this.sharedPreferencesData = sharedPreferencesData;
    }

    //@Override
//    public Observable<CurrentForecastContract> getSavedCurrentWeather(String locationId) {
//        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
//            return apiData.getCurrentWeather(locationId)
//                    .map(new Function<CurrentWeatherApiModel, CurrentForecastAdapter>() {
//                        @Override
//                        public CurrentForecastAdapter apply(@NonNull CurrentWeatherApiModel apiModel) throws Exception {
//                            CurrentForecastAdapter model = AppModelFactory.create(apiModel);
//                            dbData.saveCurrentWeather(model.getAdaptee()).subscribe();
//                            return model;
//                        }
//                    });
//        }
//        return dbData.getSavedCurrentWeather(locationId).map(new Function<CurrentForecastEntity, CurrentForecastAdapter>() {
//            @Override
//            public CurrentForecastAdapter apply(@NonNull CurrentForecastEntity currentWeatherEntity) throws Exception {
//                return AppModelFactory.create(currentWeatherEntity);
//            }
//        });
//    }
//
//    @Override
//    public Observable<HourlyForecastWeatherModel> getSavedHourlyForecastWeather(String locationId) {
//        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
//            return apiData.getHourlyForecastWeather(locationId)
//                    .map(new Function<HourlyForecastWeatherApiModel, HourlyForecastWeatherAdapterModel>() {
//                        @Override
//                        public HourlyForecastWeatherApiModel apply(@NonNull HourlyForecastWeatherApiModel apiModel) throws Exception {
//                            HourlyForecastWeatherAdapterModel model = AppModelFactory.create(apiModel);
//                            dbData.saveHourlyForecastWeather(model.getAdaptee()).subscribe();
//                            return model;
//                        }
//                    });
//        }
//        return dbData.getSavedCurrentWeather(locationId).map(new Function<CurrentForecastEntity, CurrentForecastAdapter>() {
//            @Override
//            public CurrentForecastAdapter apply(@NonNull CurrentForecastEntity currentWeatherEntity) throws Exception {
//                return AppModelFactory.create(currentWeatherEntity);
//            }
//        });
//    }
//
//    @Override
//    public Observable<DailyForecastAdapter> getSavedDailyForecastWeather(String locationId) {
//        return null;
//    }
//
//    @Override
//    public Observable<List<LocationAdapter>> getAllLocationsByName(String locationName, int resultsCount) {
//        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
//            return apiData.getLocationsByName(locationName, resultsCount)
//                    .map(new Function<LocationForecastApiModel, List<LocationAdapter>>() {
//                        @Override
//                        public List<LocationAdapter> apply(@NonNull LocationForecastApiModel locations) throws Exception {
//                            List<LocationAdapter> locationAdapters = new ArrayList<>();
//                            for (LocationWeatherApiModel apiModel : locations.locationApiModelList) {
//                                locationAdapters.add(AppModelFactory.create(apiModel));
//                            }
//                            return locationAdapters;
//                        }
//                    });
//        }
//        ;
//        return Observable.error(new NoNetworkException());
//    }
//
//    @Override
//    public Observable<LocationAdapter> getChosenLocation() {
//        return dbData.getChosenLocation().map(new Function<LocationEntity, LocationAdapter>() {
//            @Override
//            public LocationAdapter apply(@NonNull LocationEntity locationEntity) throws Exception {
//                return AppModelFactory.create(locationEntity);
//            }
//        });
//
//    }
//
//    @Override
//    public Observable<List<LocationAdapter>> getSavedLocations() {
//
//        return dbData.getSavedLocations().map(new Function<List<LocationEntity>, List<LocationAdapter>>() {
//            @Override
//            public List<LocationAdapter> apply(@NonNull List<LocationEntity> locationEntities) throws Exception {
//                List<LocationAdapter> locationAdapters = new ArrayList<>();
//                for (LocationEntity locationEntity : locationEntities) {
//                    locationAdapters.add(AppModelFactory.create(locationEntity));
//                }
//                return locationAdapters;
//            }
//        });
//    }
//
//    @Override
//    public Observable<Boolean> saveNewLocation(LocationAdapter locationAdapter) {
//        return dbData.saveNewLocation(locationAdapter.getAdaptee());
//    }
//
//    @Override
//    public Observable<Boolean> updateLocationPosition(final int oldPosition, final int newPosition) {
//        final List<LocationEntity> locations = dbData.getSavedLocations().blockingSingle();
//        return Observable.fromCallable(new Callable<Boolean>() {
//            @Override
//            public Boolean call() {
//                return updateEntitiesOrderPosition(locations, oldPosition, newPosition);
//            }
//        });
//    }
//
//    @Override
//    public Observable<Boolean> removeLocation(LocationAdapter locationAdapter) {
//        return dbData.removeLocation(locationAdapter.getAdaptee());
//    }
//
//    @Override
//    public boolean getCanUseCurrentLocationPreference() {
//        return sharedPreferencesData.getCanUseCurrentLocationPreference();
//    }
//
//    @Override
//    public UnitsAppModel getUnits() {
//        return sharedPreferencesData.getUnits();
//    }
//

    @Override
    public Single<? extends CurrentForecastAdapterContract> getSavedCurrentWeather(@NonNull LocationContract location) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getCurrentWeather(location)
                    .map(new Function<CurrentWeatherApiModel, CurrentForecastAdapterContract>() {
                        @Override
                        public CurrentForecastAdapterContract apply(@NonNull CurrentWeatherApiModel apiModel) throws Exception {
                            CurrentForecastAdapterContract model = AppModelFactory.create(apiModel);
                            dbData.saveCurrentWeather(model.getAdaptee()).subscribe();
                            return model;
                        }
                    });
        }
        return dbData.getSavedCurrentWeather(location)
                .map(new Function<CurrentForecastEntity, CurrentForecastAdapter>() {
            @Override
            public CurrentForecastAdapter apply(@NonNull CurrentForecastEntity currentWeatherEntity) throws Exception {
                return AppModelFactory.create(currentWeatherEntity);
            }
        });
    }

    @Override
    public Single<HourlyForecastAdapterContract> getSavedHourlyForecastWeather(LocationContract location) {
        return null;
    }

    @Override
    public Single<DailyForecastAdapterContract> getSavedDailyForecastWeather(LocationContract location) {
        return null;
    }

    @Override
    public Single<List<LocationContract>> getAllLocationsByName(String locationName, int resultsCount) {
        return null;
    }

    @Override
    public Single<? extends LocationContract> getChosenLocation() {
        return null;
    }

    @Override
    public Single<List<LocationContract>> getSavedLocations() {
        return null;
    }

    @Override
    public Completable saveNewLocation(LocationContract location) {
        return null;
    }

    @Override
    public Completable removeLocation(LocationContract location) {
        return null;
    }

    @Override
    public boolean getCanUseCurrentLocationPreference() {
        return false;
    }

    @Override
    public Single<UnitsContract> getUnits() {
        return null;
    }

    @Override
    public Completable updateLocationPosition(int fromPosition, int toPosition) {
        return null;
    }

    private Boolean updateEntitiesOrderPosition(List<LocationEntity> locations, int oldPosition, int newPosition) {
        updateCollectionItemsOrder(locations, oldPosition, newPosition);
        boolean areAnyLocationUpdated = false;
        for (LocationEntity entity : locations) {
            int newIndex = locations.indexOf(entity);
            if (newIndex != entity.getPosition()) {
                areAnyLocationUpdated = true;
                entity.setPosition(newIndex);
                dbData.updateLocation(entity).subscribeOn(Schedulers.io()).subscribe();
            }
        }
        return areAnyLocationUpdated;
    }

    private void updateCollectionItemsOrder(List<LocationEntity> locations, int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            toPosition++;
        }
        Log.d(TAG, "from = " + fromPosition + "; to = " + toPosition);
        locations.add(toPosition, locations.get(fromPosition));
        int toRemovePosition = fromPosition > toPosition ? fromPosition + 1 : fromPosition;
        Log.d(TAG, "Item to remove position is " + toRemovePosition);
        locations.remove(toRemovePosition);
    }

    private ForecastSource getForecastSource() {
        return ForecastSource.OPEN_WEATHER_MAP;
    }
}
