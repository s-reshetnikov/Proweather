package by.reshetnikov.proweather.data;

import android.location.Location;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.contracts.AppDataContract;
import by.reshetnikov.proweather.data.local.AppLocalData;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import by.reshetnikov.proweather.model.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.model.appmodels.CityAppModel;
import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.ForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.model.dbmodels.CityEntity;
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

        CityEntity cityEntity = appLocalData.getChosenCity();
        if (cityEntity == null)
            return null;

        Single<CurrentWeather> weatherSingle = appRemoteData.getCurrentWeather(cityEntity.getCityId());
        saveToDb(weatherSingle);
        return getCurrentWeatherAppModelFromCurrentWeatherApi(weatherSingle);
    }

    @Override
    public Single<ForecastWeatherAppModel> getForecastWeather() {
//        if (appLocalData.canUseCurrentLocation()) {
//            Location currentLocation = appLocalData.getCurrentLocation();
//            if (currentLocation != null && NetworkUtils.isNetworkConnected(weatherApp)) {
//                Single<CurrentWeather> weatherSingle =
//                        appRemoteData.getCurrentWeather(currentLocation.getLatitude(), currentLocation.getLongitude());
//                saveToDb(weatherSingle);
//                return getCurrentWeatherAppModelFromCurrentWeatherApi(weatherSingle);
//            }
//        }
//
//        CityEntity cityEntity = appLocalData.getChosenCity();
//        Single<CurrentWeather> weatherSingle = appRemoteData.getCurrentWeather(cityEntity.getCityId());
//        saveToDb(weatherSingle);
//        return getCurrentWeatherAppModelFromCurrentWeatherApi(weatherSingle);
        return null;
    }

    @Override
    public Single<List<CityAppModel>> getSavedCities() {
        return null;
    }

    @Override
    public void saveCity(CityAppModel city) {

    }

    @Override
    public boolean canUseCurrentLocation() {
        return appLocalData.canUseCurrentLocation();
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
