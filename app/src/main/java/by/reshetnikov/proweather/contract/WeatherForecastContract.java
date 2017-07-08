package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeather;


public interface WeatherForecastContract {

    interface View extends BaseView<WeatherForecastContract.Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends BasePresenter {
        HourlyForecastWeather getForecastWeather();

        void setView(View view);
    }
}