package by.reshetnikov.proweather.data;

import android.annotation.SuppressLint;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.db.DbContract;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.exception.NoNetworkException;
import by.reshetnikov.proweather.data.model.Coordinates;
import by.reshetnikov.proweather.data.model.OWMModelToDbModelFactory;
import by.reshetnikov.proweather.data.model.location.LocationFactory;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.network.WeatherApiDataContract;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationWeatherApiModel;
import by.reshetnikov.proweather.data.preferences.PreferencesContract;
import by.reshetnikov.proweather.di.qualifier.BalancedPowerAccuracy;
import by.reshetnikov.proweather.di.qualifier.LowPower;
import by.reshetnikov.proweather.utils.NetworkUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class DataManager implements DataContract {

    private DbContract dbData;
    private WeatherApiDataContract apiData;
    private PreferencesContract sharedPreferencesData;
    private RxLocation rxLocation;

    private LocationRequest lowPowerLocationRequest;
    private LocationRequest balancedPowerLocationRequest;

    @Inject
    public DataManager(DbContract dbData,
                       WeatherApiDataContract apiData,
                       PreferencesContract sharedPreferencesData,
                       RxLocation rxLocation
    ) {
        this.dbData = dbData;
        this.apiData = apiData;
        this.sharedPreferencesData = sharedPreferencesData;
        this.rxLocation = rxLocation;
    }

    @Inject
    void initializeLocationRequests(@LowPower LocationRequest lowPower, @BalancedPowerAccuracy LocationRequest balancedPower) {
        this.lowPowerLocationRequest = lowPower;
        this.balancedPowerLocationRequest = balancedPower;
    }

    @Override
    public Single<NowForecastEntity> getNowForecast(@NonNull LocationEntity location) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getCurrentForecast(location)
                    .map(new Function<CurrentForecastApiModel, NowForecastEntity>() {
                        @Override
                        public NowForecastEntity apply(@NonNull CurrentForecastApiModel apiModel) {
                            return OWMModelToDbModelFactory.createNowForecastFromAPI(apiModel);
                        }
                    })
                    .flatMap(new Function<NowForecastEntity, SingleSource<NowForecastEntity>>() {
                        @Override
                        public SingleSource<NowForecastEntity> apply(NowForecastEntity nowForecastEntity) throws Exception {
                            Timber.d("try to save now forecast");
                            return dbData.saveCurrentWeather(nowForecastEntity).toSingle(new Callable<NowForecastEntity>() {
                                @Override
                                public NowForecastEntity call() throws Exception {
                                    Timber.d("return now forecast");
                                    return nowForecastEntity;
                                }
                            });
                        }
                    });
        }
        return dbData.getSavedNowForecast(location)
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                });
    }

    @Override
    public Single<List<HoursForecastEntity>> getHourForecasts(@NonNull LocationEntity location) {
        Timber.d("getHourForecasts(), Is location null? " + (location == null));
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getHourlyForecast(location)
                    .map(new Function<HourlyForecastApiModel, List<HoursForecastEntity>>() {
                        @Override
                        public List<HoursForecastEntity> apply(@NonNull HourlyForecastApiModel apiModel) {
                            return OWMModelToDbModelFactory.createHourlyForecastsFromAPI(apiModel);
                        }
                    })
                    .flatMap(new Function<List<HoursForecastEntity>, SingleSource<List<HoursForecastEntity>>>() {
                        @Override
                        public SingleSource<List<HoursForecastEntity>> apply(List<HoursForecastEntity> hoursForecastEntities) throws Exception {
                            return dbData.saveHourlyForecasts(hoursForecastEntities).toSingle(new Callable<List<HoursForecastEntity>>() {
                                @Override
                                public List<HoursForecastEntity> call() throws Exception {
                                    return hoursForecastEntities;
                                }
                            });
                        }
                    }).doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Timber.e(throwable);
                        }
                    });
        }
        return dbData.getSavedHourlyForecast(location);
    }

    @Override
    public Single<List<DailyForecastEntity>> getDailyForecasts(@NonNull LocationEntity location) {
        Timber.d("getDailyForecasts(), Is location null? " + (location == null));
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getDailyForecast(location)
                    .map(new Function<HourlyForecastApiModel, List<DailyForecastEntity>>() {
                        @Override
                        public List<DailyForecastEntity> apply(@NonNull HourlyForecastApiModel apiModel) throws Exception {
                            Timber.d("received models = " + apiModel.forecasts.size());
                            List<DailyForecastEntity> dayForecasts = OWMModelToDbModelFactory.createDailyForecastsFromAPI(apiModel);
                            Timber.d("view model size = " + dayForecasts.size());
                            return dayForecasts;
                        }
                    })
                    .flatMap(new Function<List<DailyForecastEntity>, SingleSource<List<DailyForecastEntity>>>() {
                        @Override
                        public SingleSource<List<DailyForecastEntity>> apply(List<DailyForecastEntity> dailyForecastEntities) throws Exception {
                            return dbData.saveDailyForecasts(dailyForecastEntities).toSingle(new Callable<List<DailyForecastEntity>>() {
                                @Override
                                public List<DailyForecastEntity> call() throws Exception {
                                    return dailyForecastEntities;
                                }
                            });
                        }
                    }).doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Timber.e(throwable);
                        }
                    });
        }
        return dbData.getSavedDailyForecast(location);
    }

    @Override
    public Single<Coordinates> getLastSavedLocation() {
        return sharedPreferencesData.getLastSavedCoordinates();
    }

    @Override
    public Single<Coordinates> getSavedLastLocations() {
        return sharedPreferencesData.getLastSavedCoordinates();
    }

    @Override
    public Completable saveLastLocation(Coordinates coordinates) {
        return sharedPreferencesData.saveLastCoordinates(coordinates);
    }

    @SuppressLint("MissingPermission")
    @Override
    public Observable<Coordinates> getBalancedPowerLastCoordinates() {
        Timber.d("getBalancedPowerLastCoordinates called");
        return rxLocation
                .location()
                .updates(balancedPowerLocationRequest)
                .map(new Function<Location, Coordinates>() {
                    @Override
                    public Coordinates apply(Location location) throws Exception {
                        Timber.d("getBalancedPowerLastCoordinates() called");
                        return new Coordinates(location.getLatitude(), location.getLongitude());
                    }
                });
    }

    @SuppressLint("MissingPermission")
    @Override
    public Observable<Coordinates> getLowPowerLastCoordinates() {
        Timber.d("getLowPowerLastCoordinates called");
        return rxLocation
                .location()
                .updates(lowPowerLocationRequest)
                .map(new Function<Location, Coordinates>() {
                    @Override
                    public Coordinates apply(Location location) throws Exception {
                        Timber.d("getLowPowerLastCoordinates() called");
                        return new Coordinates(location.getLatitude(), location.getLongitude());
                    }
                });
    }

    @Override
    public Single<List<LocationEntity>> getAllLocationsByName(String locationName, int resultsCount) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getLocationsByName(locationName, resultsCount)
                    .map(new Function<LocationForecastApiModel, List<LocationEntity>>() {
                        @Override
                        public List<LocationEntity> apply(@NonNull LocationForecastApiModel locations) {
                            List<LocationEntity> locationAdapters = new ArrayList<>();
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
    public Single<List<LocationEntity>> getLocationsByCoordinates(double latitude, double longitude, int resultsCount) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getLocationsByCoordinates(latitude, longitude, resultsCount)
                    .map(new Function<LocationForecastApiModel, List<LocationEntity>>() {
                        @Override
                        public List<LocationEntity> apply(@NonNull LocationForecastApiModel locations) {
                            List<LocationEntity> locationAdapters = new ArrayList<>();
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
    public Single<LocationEntity> getChosenLocation() {
        return dbData.getChosenLocation();
    }

    @Override
    public Single<List<LocationEntity>> getSavedLocations() {
        return dbData.getSavedLocations();
    }

    @Override
    public Completable saveNewLocation(@NonNull LocationEntity location) {
        return dbData.saveNewLocation(location);
    }

    @Override
    public Completable saveOrUpdateLocation(@NonNull LocationEntity location) {
        return dbData.saveOrUpdateLocation(location);
    }

    @Override
    public Completable removeLocation(@NonNull LocationEntity location) {
        return dbData.removeLocation(location);
    }

    @Override
    public boolean canUseCurrentLocation() {
        return sharedPreferencesData.getCanUseCurrentLocationPreference();
    }

    @Override
    public Single<Units> getUnits() {
        return Single.just(sharedPreferencesData.getUnits());
    }

    @Override
    public Completable updateLocationPositions(final List<LocationEntity> locations) {
        return dbData.updateLocations(locations);
    }

}
