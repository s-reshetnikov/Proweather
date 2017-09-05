package by.reshetnikov.proweather.data;

import android.util.Log;

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
import by.reshetnikov.proweather.data.model.OWMModelToDbModelFactory;
import by.reshetnikov.proweather.data.model.location.LocationFactory;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.network.WeatherApiDataContract;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationWeatherApiModel;
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import by.reshetnikov.proweather.utils.NetworkUtils;
import by.reshetnikov.proweather.utils.scheduler.AppSchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

@Singleton
public class DataManager implements DataContract {

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
    public Single<LocationEntity> getChosenLocation() {
        Timber.d("getSavedLocation()");
        return dbData.getChosenLocation();
    }

    @Override
    public Single<List<LocationEntity>> getSavedLocations() {
        return dbData.getSavedLocations();
    }

    @Override
    public Completable saveNewLocation(@NonNull LocationEntity location) {
        if (location instanceof LocationEntity)
            return dbData.saveNewLocation(location);
        return Completable.error(new ClassCastException());
    }

    @Override
    public Completable removeLocation(@NonNull LocationEntity location) {
        if (location instanceof LocationEntity)
            return dbData.removeLocation(location);
        return Completable.error(new ClassCastException());
    }

    @Override
    public boolean getCanUseCurrentLocationPreference() {
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
