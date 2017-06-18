package by.reshetnikov.proweather.data;

import java.util.Map;

import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by SacRahl on 6/6/2017.
 */

public interface AppDataContract {

    Observable<CurrentWeather> getCurrentWeather();

    Observable<ForecastWeather> getForecastWeather(ForecastType forecastType);

    Single<Map<String, String>> getSettings();

    void saveSettings(Map<String, String> strings);

}
