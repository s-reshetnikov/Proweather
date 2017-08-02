package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;


public interface WeatherForecastContract {

    interface View extends ViewContract<Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends PresenterContract {
        HourlyForecastWeatherApiModel getForecastWeather();

        void setView(View view);
    }
}