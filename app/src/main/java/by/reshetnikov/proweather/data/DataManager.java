package by.reshetnikov.proweather.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.db.DbContract;
import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.data.model.location.LocationFactory;
import by.reshetnikov.proweather.data.model.unit.UnitsContract;
import by.reshetnikov.proweather.data.model.weather.WeatherModelFactory;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapterContract;
import by.reshetnikov.proweather.data.model.weather.hourly.HourlyForecastAdapterContract;
import by.reshetnikov.proweather.data.network.WeatherApiDataContract;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationWeatherApiModel;
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import by.reshetnikov.proweather.exception.NoNetworkException;
import by.reshetnikov.proweather.utils.NetworkUtils;
import by.reshetnikov.proweather.utils.scheduler.AppSchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DataManager implements DataContract {

    private static final String TAG = DataManager.class.getSimpleName();

    private DbContract dbData;
    private WeatherApiDataContract apiData;
    private AppSharedPreferencesData sharedPreferencesData;
    private AppSchedulerProvider scheduler;

    @Inject
    public DataManager(DbContract dbData,
                       WeatherApiDataContract apiData,
                       AppSharedPreferencesData sharedPreferencesData
    ) {
        this.dbData = dbData;
        this.apiData = apiData;
        this.sharedPreferencesData = sharedPreferencesData;
    }

    @Override
    public Single<? extends CurrentForecastAdapterContract> getSavedCurrentWeather(@NonNull LocationContract location) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getCurrentForecast(location)
                    .map(new Function<CurrentForecastApiModel, CurrentForecastAdapterContract>() {
                        @Override
                        public CurrentForecastAdapterContract apply(@NonNull CurrentForecastApiModel apiModel) throws Exception {
                            CurrentForecastAdapterContract model = WeatherModelFactory.createCurrentForecastAdapter(apiModel);
                            dbData.saveCurrentWeather(model.getAdaptee()).subscribe();
                            return model;
                        }
                    });
        }
        return dbData.getSavedCurrentWeather(location)
                .map(new Function<CurrentForecastEntity, CurrentForecastAdapterContract>() {
                    @Override
                    public CurrentForecastAdapterContract apply(@NonNull CurrentForecastEntity currentForecast) throws Exception {
                        return WeatherModelFactory.createCurrentForecastAdapter(currentForecast);
                    }
                });
    }

    @Override
    public Single<HourlyForecastAdapterContract> getSavedHourlyForecastWeather(@NonNull LocationContract location) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getHourlyForecast(location)
                    .map(new Function<HourlyForecastApiModel, HourlyForecastAdapterContract>() {
                        @Override
                        public HourlyForecastAdapterContract apply(@NonNull HourlyForecastApiModel apiModel) throws Exception {
                            HourlyForecastAdapterContract model = WeatherModelFactory.createHourlyForecastAdapter(apiModel);
                            dbData.saveHourlyForecastWeather(model.getAdaptee()).subscribe();
                            return model;
                        }
                    });
        }
        return dbData.getSavedHourlyForecast(location)
                .map(new Function<HourlyForecastEntity, HourlyForecastAdapterContract>() {
                    @Override
                    public HourlyForecastAdapterContract apply(@NonNull HourlyForecastEntity entity) throws Exception {
                        return WeatherModelFactory.createHourlyForecastAdapter(entity);
                    }
                });
    }

    @Override
    public Single<List<LocationContract>> getAllLocationsByName(String locationName, int resultsCount) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getLocationsByName(locationName, resultsCount)
                    .map(new Function<LocationForecastApiModel, List<LocationContract>>() {
                        @Override
                        public List<LocationContract> apply(@NonNull LocationForecastApiModel locations) throws Exception {
                            List<LocationContract> locationAdapters = new ArrayList<>();
                            for (LocationWeatherApiModel apiModel : locations.locationApiModelList) {
                                locationAdapters.add(LocationFactory.create(apiModel));
                            }
                            return locationAdapters;
                        }
                    });
        }

        return Single.error(new NoNetworkException());
    }

    @Override
    public Single<? extends LocationContract> getChosenLocation() {
        return dbData.getChosenLocation().map(new Function<LocationEntity, LocationContract>() {
            @Override
            public LocationContract apply(@NonNull LocationEntity locationEntity) throws Exception {
                return LocationFactory.create(locationEntity);
            }
        });
    }

    @Override
    public Single<List<LocationContract>> getSavedLocations() {
        return dbData.getSavedLocations().map(new Function<List<LocationEntity>, List<LocationContract>>() {
            @Override
            public List<LocationContract> apply(@NonNull List<LocationEntity> locationEntities) throws Exception {
                List<LocationContract> locationAdapters = new ArrayList<>();
                for (LocationEntity locationEntity : locationEntities) {
                    locationAdapters.add(LocationFactory.create(locationEntity));
                }
                return locationAdapters;
            }
        });
    }

    @Override
    public Completable saveNewLocation(@NonNull LocationContract location) {
        return dbData.saveNewLocation(location.getAdaptee());
    }

    @Override
    public Completable removeLocation(LocationContract location) {
        return dbData.removeLocation(location.getAdaptee());
    }

    @Override
    public boolean getCanUseCurrentLocationPreference() {
        return sharedPreferencesData.getCanUseCurrentLocationPreference();
    }

    @Override
    public Single<UnitsContract> getUnits() {
        return Single.just(sharedPreferencesData.getUnits());
    }

    @Override
    public Completable updateLocationPosition(final int fromPosition, final int toPosition) {
        final List<LocationEntity> locations = dbData.getSavedLocations().blockingGet();
        return Completable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return updateEntitiesOrderPosition(locations, fromPosition, toPosition);
            }
        });
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
