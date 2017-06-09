package by.reshetnikov.proweather.data;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.db.AppLocalData;
import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.data.models.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import by.reshetnikov.proweather.utils.NetworkUtils;
import io.reactivex.Observable;

/**
 * Created by SacRahl on 5/31/2017.
 */

public class DataRepository implements AppDataContract {

    @Inject
    ProWeatherApp weatherApp;
    private AppLocalData appLocalData;
    private AppRemoteData appRemoteData;

    @Inject
    public DataRepository(AppLocalData appLocalData, AppRemoteData appRemoteData) {
        this.appLocalData = appLocalData;
        this.appRemoteData = appRemoteData;
    }

    @Override
    public Observable<CurrentWeather> getCurrentWeather() {
        if (NetworkUtils.isNetworkConnected(weatherApp)) {
            return appRemoteData.getCurrentWeather();
        }
        return appLocalData.getCurrentWeather();
    }

    @Override
    public Observable<ForecastWeather> getForecastWeather(ForecastType forecastType) {
        if (NetworkUtils.isNetworkConnected(weatherApp)) {
            return appRemoteData.getForecastWeather(forecastType);
        }
        return appLocalData.getForecastWeather(forecastType);
    }
}
