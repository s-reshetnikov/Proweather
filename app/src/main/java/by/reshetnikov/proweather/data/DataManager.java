package by.reshetnikov.proweather.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import by.reshetnikov.proweather.di.qualifier.HighAccuracy;
import by.reshetnikov.proweather.di.qualifier.LowPower;
import by.reshetnikov.proweather.utils.NetworkUtils;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

@Singleton
public class DataManager implements DataContract {

    private DbContract dbData;
    private WeatherApiDataContract apiData;
    private AppSharedPreferencesData sharedPreferencesData;
    private RxLocation rxLocation;
    private Context context;

    private LocationRequest lowPowerLocationRequest;
    private LocationRequest highAccuracyLocationRequest;

    @Inject
    public DataManager(DbContract dbData,
                       WeatherApiDataContract apiData,
                       AppSharedPreferencesData sharedPreferencesData,
                       RxLocation rxLocation
    ) {
        this.dbData = dbData;
        this.apiData = apiData;
        this.sharedPreferencesData = sharedPreferencesData;
        this.rxLocation = rxLocation;
    }

    @Inject
    void initializeLocationRequests(@LowPower LocationRequest lowPower, @HighAccuracy LocationRequest highAccuracy) {
        this.lowPowerLocationRequest = lowPower;
        this.highAccuracyLocationRequest = highAccuracy;
    }

    @Override
    public Single<NowForecastEntity> getNowForecast(@NonNull LocationEntity location) {
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getCurrentForecast(location)
                    .map(new Function<CurrentForecastApiModel, NowForecastEntity>() {
                        @Override
                        public NowForecastEntity apply(@NonNull CurrentForecastApiModel apiModel) throws Exception {
                            NowForecastEntity model = OWMModelToDbModelFactory.createNowForecastFromAPI(apiModel);
//                            dbData.saveCurrentWeather(model).subscribe();
                            return model;
                        }
                    });
        }
        return dbData.getSavedNowForecast(location);
    }

    @Override
    public Single<List<HoursForecastEntity>> getHourForecasts(@NonNull LocationEntity location) {
        Timber.d("getHourForecasts(), Is location null? " + (location == null));
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getHourlyForecast(location)
                    .map(new Function<HourlyForecastApiModel, List<HoursForecastEntity>>() {
                        @Override
                        public List<HoursForecastEntity> apply(@NonNull HourlyForecastApiModel apiModel) throws Exception {
                            return OWMModelToDbModelFactory.createHourlyForecastsFromAPI(apiModel);
                        }
                    });
        }
        return dbData.getSavedHourlyForecast(location);
    }

    @Override
    public Single<List<DailyForecastEntity>> getDailyForecasts(LocationEntity location) {
        Timber.d("getDailyForecasts(), Is location null? " + (location == null));
        if (NetworkUtils.isNetworkConnected(ProWeatherApp.getAppContext())) {
            return apiData.getDailyForecast(location)
                    .map(new Function<HourlyForecastApiModel, List<DailyForecastEntity>>() {
                        @Override
                        public List<DailyForecastEntity> apply(@NonNull HourlyForecastApiModel apiModel) throws Exception {
                            Log.i("DM", "api model size = " + apiModel.forecasts.size());
                            List<DailyForecastEntity> dayModels = OWMModelToDbModelFactory.createDailyForecastsFromAPI(apiModel);
                            Log.i("DM", "view model size = " + dayModels.size());
                            return dayModels;
                        }
                    });
        }
        return dbData.getSavedDailyForecast(location);
    }

    @SuppressLint("MissingPermission")
    @Override
    public Observable<Coordinates> getLastCoordinates() {

        return rxLocation
                .location()
                .updates(highAccuracyLocationRequest)
                .map(new Function<Location, Coordinates>() {
                    @Override
                    public Coordinates apply(Location location) throws Exception {
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
                        public List<LocationEntity> apply(@NonNull LocationForecastApiModel locations) throws Exception {
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
                        public List<LocationEntity> apply(@NonNull LocationForecastApiModel locations) throws Exception {
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
        Timber.d("getChosenLocation()");
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
        //return dbData.updateLocationMarkersWithZoom(locations);
        return Completable.complete();
    }

}
