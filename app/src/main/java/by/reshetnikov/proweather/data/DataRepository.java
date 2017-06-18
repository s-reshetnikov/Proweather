package by.reshetnikov.proweather.data;

import java.util.Map;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.data.db.AppLocalData;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import by.reshetnikov.proweather.utils.NetworkUtils;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by SacRahl on 5/31/2017.
 */

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

    @Override
    public Single<Map<String, String>> getSettings() {
        return null;
    }

    @Override
    public void saveSettings(Map<String, String> strings) {

    }
}
