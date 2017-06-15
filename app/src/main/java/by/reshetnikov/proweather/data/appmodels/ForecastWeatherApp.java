package by.reshetnikov.proweather.data.appmodels;

import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;

/**
 * Created by SacRahl on 6/12/2017.
 */

public class ForecastWeatherApp {

    private final ForecastWeather weather;

    public ForecastWeatherApp(ForecastWeather weather) {
        this.weather = weather;
    }
}
