package by.reshetnikov.proweather.data;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.AppLocalData;
import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.data.models.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import io.reactivex.Observable;

/**
 * Created by SacRahl on 5/31/2017.
 */

public class RepositoryWeather implements AppDataContract {

    private AppLocalData appLocalData;
    private AppRemoteData appRemoteData;

    @Inject
    public RepositoryWeather(AppLocalData appLocalData, AppRemoteData appRemoteData) {
        this.appLocalData = appLocalData;
        this.appRemoteData = appRemoteData;
    }

    @Override
    public Observable<CurrentWeather> getCurrentWeather() {
        return null;
    }

    @Override
    public Observable<ForecastWeather> getForecastWeather(ForecastType forecastType) {
        return null;
    }
}
