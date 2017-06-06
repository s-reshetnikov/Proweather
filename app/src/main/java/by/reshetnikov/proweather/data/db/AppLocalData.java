package by.reshetnikov.proweather.data.db;

import android.content.Context;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.AppDataContract;
import by.reshetnikov.proweather.data.ForecastType;
import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.data.models.ForecastWeatherModels.ForecastWeather;
import io.reactivex.Observable;

/**
 * Created by SacRahl on 6/6/2017.
 */

public class AppLocalData implements AppDataContract {

    Context context;

    @Inject
    public AppLocalData(Context context) {
        ProWeatherApp.getProWeatherApp().getAppComponent().injectLocalData(this);
        this.context = context;
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
