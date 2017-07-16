package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;


public interface WeatherForecastContract {

    interface View extends BaseView<WeatherForecastContract.Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends BasePresenter {
        HourlyForecastWeatherApiModel getForecastWeather();

        void setView(View view);
    }
}