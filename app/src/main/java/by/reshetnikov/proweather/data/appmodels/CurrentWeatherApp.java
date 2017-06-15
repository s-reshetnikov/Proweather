package by.reshetnikov.proweather.data.appmodels;

import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;

/**
 * Created by SacRahl on 6/12/2017.
 */

public class CurrentWeatherApp {
    private final CurrentWeather weather;

    public CurrentWeatherApp(CurrentWeather weather) {
        this.weather = weather;
    }
}
