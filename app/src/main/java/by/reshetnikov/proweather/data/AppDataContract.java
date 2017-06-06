package by.reshetnikov.proweather.data;

import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.data.models.ForecastWeatherModels.ForecastWeather;
import io.reactivex.Observable;

/**
 * Created by SacRahl on 6/6/2017.
 */

public interface AppDataContract {

    Observable<CurrentWeather> getCurrentWeather();

    Observable<ForecastWeather> getForecastWeather(ForecastType forecastType);

}
