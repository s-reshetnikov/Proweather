package by.reshetnikov.proweather.data;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.data.appmodels.CityAppModel;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.ForecastWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.LocationAppModel;
import by.reshetnikov.proweather.data.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.data.local.AppLocalData;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import by.reshetnikov.proweather.utils.NetworkUtils;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
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
    public Observable<CurrentWeatherAppModel> getCurrentWeather() {
        if (NetworkUtils.isNetworkConnected(weatherApp)) {
            Observable<CurrentWeather> weatherObservable = appRemoteData.getCurrentWeather();
            return weatherObservable.map(new Function<CurrentWeather, CurrentWeatherAppModel>() {
                @Override
                public CurrentWeatherAppModel apply(@NonNull CurrentWeather weather) throws Exception {
                    return new CurrentWeatherAppModel(weather);
                }
            });
        }
        return appLocalData.getCurrentWeather();
    }

    @Override
    public Observable<ForecastWeatherAppModel> getForecastWeather(ForecastType forecastType) {
        if (NetworkUtils.isNetworkConnected(weatherApp)) {
            Observable<ForecastWeather> forecast = appRemoteData.getForecastWeather(forecastType);
            return forecast.map(new Function<ForecastWeather, ForecastWeatherAppModel>() {
                @Override
                public ForecastWeatherAppModel apply(@NonNull ForecastWeather forecastWeather) throws Exception {
                    return new ForecastWeatherAppModel(forecastWeather);
                }
            });
        }
        return appLocalData.getForecastWeather(forecastType);
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
    public LocationAppModel getCurrentLocation() {
        return appLocalData.getCurrentLocation();
    }

    @Override
    public Observable<UnitsAppModel> getUnits() {
        return appLocalData.getUnits();
    }

}
