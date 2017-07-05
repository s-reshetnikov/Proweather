package by.reshetnikov.proweather.data;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.contract.AppDataContract;
import by.reshetnikov.proweather.data.local.AppLocalData;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import by.reshetnikov.proweather.model.apimodels.currentweather.CurrentWeather;
import by.reshetnikov.proweather.model.apimodels.location.LocationForecast;
import by.reshetnikov.proweather.model.apimodels.location.LocationWeather;
import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.DailyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.HourlyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.model.dbmodels.LocationEntity;
import by.reshetnikov.proweather.utils.NetworkUtils;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class DataRepository implements AppDataContract {

    @Inject
    ProWeatherApp weatherApp;

    @Inject
    AppLocalData appLocalData;

    @Inject
    AppRemoteData appRemoteData;

    public DataRepository() {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
    }

    @Override
    public Single<CurrentWeatherAppModel> getCurrentWeather() {
        if (appLocalData.canUseCurrentLocation()) {
            Location currentLocation = appLocalData.getCurrentLocation();
            if (currentLocation != null && NetworkUtils.isNetworkConnected(weatherApp)) {
                Single<CurrentWeather> weatherSingle =
                        appRemoteData.getCurrentWeather(currentLocation.getLatitude(), currentLocation.getLongitude());
                saveToDb(weatherSingle);
                return getCurrentWeatherAppModelFromCurrentWeatherApi(weatherSingle);
            }
        }

        LocationEntity locationEntity = appLocalData.getChosenLocation();
        if (locationEntity == null)
            return null;

        Single<CurrentWeather> weatherSingle = appRemoteData.getCurrentWeather(locationEntity.getLocationId());
        saveToDb(weatherSingle);
        return getCurrentWeatherAppModelFromCurrentWeatherApi(weatherSingle);
    }

    @Override
    public Single<CurrentWeatherAppModel> getCurrentWeather(double latitude, double longitude) {
        return null;
    }

    @Override
    public Single<HourlyForecastWeatherAppModel> getHourlyForecastWeather() {
        return null;
    }

    @Override
    public Single<HourlyForecastWeatherAppModel> getHourlyForecastWeather(double latitude, double longitude) {
        return null;
    }

    @Override
    public Single<DailyForecastWeatherAppModel> getDailyForecastWeather() {
        return null;
    }

    @Override
    public Single<DailyForecastWeatherAppModel> getDailyForecastWeather(double latitude, double longitude) {
        return null;
    }

    @Override
    public boolean canUseCurrentLocation() {
        return appLocalData.canUseCurrentLocation();
    }

    @Override
    public LocationAppModel getChosenLocation() {
        return AppDataModelsBuilder.createLocationAppModel(appLocalData.getChosenLocation());
    }

    @Override
    public Single<List<LocationAppModel>> getSavedLocations() {
        return appLocalData.getLocations().map(new Function<List<LocationEntity>, List<LocationAppModel>>() {
            @Override
            public List<LocationAppModel> apply(@NonNull List<LocationEntity> locationEntities) throws Exception {
                List<LocationAppModel> locationAppModels = new ArrayList<>(locationEntities.size());
                for (LocationEntity entity : locationEntities) {
                    locationAppModels.add(AppDataModelsBuilder.createLocationAppModel(entity));
                }
                return locationAppModels;
            }
        });
    }

    @Override
    public Single<List<LocationAppModel>> getAllLocationsByName(String locationName, int count) {
        return appRemoteData.getLocations(locationName, count).map(new Function<LocationForecast, List<LocationAppModel>>() {
            @Override
            public List<LocationAppModel> apply(@NonNull LocationForecast locationForecast) throws Exception {
                List<LocationAppModel> locationAppModels =
                        new ArrayList<>(locationForecast.locationWeatherList.size());
                for (LocationWeather locationWeather : locationForecast.locationWeatherList) {
                    locationAppModels.add(AppDataModelsBuilder.createLocationAppModel(locationWeather));
                }
                return locationAppModels;
            }
        });
    }

    @Override
    public Single<List<LocationAppModel>> getAllLocationsByCoordinates(double latitude, double longitude, int count) {
        return null;
    }

    @Override
    public void saveLocation(LocationAppModel location) {
        appLocalData.saveLocation(location);
    }

    @Override
    public void removeLocation(LocationAppModel location) {
    }


    @Override
    public Single<UnitsAppModel> getUnits() {
        return appLocalData.getUnits();
    }


    private Single<CurrentWeatherAppModel> getCurrentWeatherAppModelFromCurrentWeatherApi(Single<CurrentWeather> weatherSingle) {
        return weatherSingle.map(new Function<CurrentWeather, CurrentWeatherAppModel>() {
            @Override
            public CurrentWeatherAppModel apply(@NonNull CurrentWeather currentWeather) throws Exception {
                return AppDataModelsBuilder.createCurrentWeatherModel(currentWeather);
            }
        });
    }

    private void saveToDb(Single<CurrentWeather> currentWeatherObservable) {
        currentWeatherObservable.doOnSuccess(new Consumer<CurrentWeather>() {
            @Override
            public void accept(@NonNull CurrentWeather currentWeather) throws Exception {
                appLocalData.saveCurrentWeather(AppDataModelsBuilder.createWeatherEntity(currentWeather));
            }
        });
    }

    @Override
    public void dispose() {
        appRemoteData = null;
        appLocalData = null;
    }

}
