package by.reshetnikov.proweather.data.db;

import android.content.Context;

import java.util.Map;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.AppDataContract;
import by.reshetnikov.proweather.data.ForecastType;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by SacRahl on 6/6/2017.
 */

public class AppLocalData implements AppDataContract {

    Context context;

    public AppLocalData(Context context) {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
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

    @Override
    public Single<Map<String, String>> getSettings() {
        return null;
    }

    @Override
    public void saveSettings(Map<String, String> strings) {

    }
}
