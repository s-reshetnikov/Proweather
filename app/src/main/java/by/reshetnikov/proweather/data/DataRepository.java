package by.reshetnikov.proweather.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.db.DbContract;
import by.reshetnikov.proweather.data.db.model.CurrentWeatherEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.model.AppModelFactory;
import by.reshetnikov.proweather.data.model.CurrentWeatherAdapterModel;
import by.reshetnikov.proweather.data.model.DailyForecastWeatherModel;
import by.reshetnikov.proweather.data.model.HourlyForecastWeatherModel;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.data.model.UnitsAppModel;
import by.reshetnikov.proweather.data.network.WeatherApiDataContract;
import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.location.LocationForecastApiModel;
import by.reshetnikov.proweather.data.network.model.location.LocationWeatherApiModel;
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import by.reshetnikov.proweather.exception.NoNetworkException;
import by.reshetnikov.proweather.utils.NetworkUtils;
import by.reshetnikov.proweather.utils.scheduler.AppSchedulerProvider;
import io.reactivex.Observable;
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

    @Override
    public Observable<CurrentWeatherAdapterModel> getCurrentWeather(String locationId) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getCurrentWeather(locationId)
                    .map(new Function<CurrentWeatherApiModel, CurrentWeatherAdapterModel>() {
                        @Override
                        public CurrentWeatherAdapterModel apply(@NonNull CurrentWeatherApiModel apiModel) throws Exception {
                            CurrentWeatherAdapterModel model = AppModelFactory.create(apiModel);
                            dbData.saveCurrentWeather(model.getAdaptee()).subscribeOn(scheduler.io()).subscribe();
                            return model;
                        }
                    });
        }
        return dbData.getCurrentWeather(locationId).map(new Function<CurrentWeatherEntity, CurrentWeatherAdapterModel>() {
            @Override
            public CurrentWeatherAdapterModel apply(@NonNull CurrentWeatherEntity currentWeatherEntity) throws Exception {
                return AppModelFactory.create(currentWeatherEntity);
            }
        });
    }

    @Override
    public Observable<HourlyForecastWeatherModel> getHourlyForecastWeather(String locationId) {
        return null;
    }

    @Override
    public Observable<DailyForecastWeatherModel> getDailyForecastWeather(String locationId) {
        return null;
    }

    @Override
    public Observable<List<LocationAdapterModel>> getAllLocationsByName(String locationName, int resultsCount) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getLocationsByName(locationName, resultsCount)
                    .map(new Function<LocationForecastApiModel, List<LocationAdapterModel>>() {
                        @Override
                        public List<LocationAdapterModel> apply(@NonNull LocationForecastApiModel locations) throws Exception {
                            List<LocationAdapterModel> locationAdapters = new ArrayList<>();
                            for (LocationWeatherApiModel apiModel : locations.locationApiModelList) {
                                locationAdapters.add(AppModelFactory.create(apiModel));
                            }
                            return locationAdapters;
                        }
                    });
        }
        ;
        return Observable.error(new NoNetworkException());
    }

    @Override
    public Observable<LocationAdapterModel> getChosenLocation() {
        return dbData.getChosenLocation().map(new Function<LocationEntity, LocationAdapterModel>() {
            @Override
            public LocationAdapterModel apply(@NonNull LocationEntity locationEntity) throws Exception {
                return AppModelFactory.create(locationEntity);
            }
        });

    }

    @Override
    public Observable<List<LocationAdapterModel>> getSavedLocations() {

        return dbData.getSavedLocations().map(new Function<List<LocationEntity>, List<LocationAdapterModel>>() {
            @Override
            public List<LocationAdapterModel> apply(@NonNull List<LocationEntity> locationEntities) throws Exception {
                List<LocationAdapterModel> locationAdapters = new ArrayList<>();
                for (LocationEntity locationEntity : locationEntities) {
                    locationAdapters.add(AppModelFactory.create(locationEntity));
                }
                return locationAdapters;
            }
        });
    }

    @Override
    public Observable<Boolean> saveNewLocation(LocationAdapterModel locationAdapter) {
        return dbData.saveNewLocation(locationAdapter.getAdaptee());
    }

    @Override
    public Observable<Boolean> updateLocationPosition(final int oldPosition, final int newPosition) {
        final List<LocationEntity> locations = dbData.getSavedLocations().blockingSingle();
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return updateEntitiesOrderPosition(locations, oldPosition, newPosition);
            }
        });
    }

    @Override
    public Observable<Boolean> removeLocation(LocationAdapterModel locationAdapter) {
        return dbData.removeLocation(locationAdapter.getAdaptee());
    }

    @Override
    public boolean getCanUseCurrentLocationPreference() {
        return sharedPreferencesData.getCanUseCurrentLocationPreference();
    }

    @Override
    public UnitsAppModel getUnits() {
        return sharedPreferencesData.getUnits();
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

}
