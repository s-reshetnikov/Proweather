package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.model.apimodels.ForecastWeatherModels.HourlyForecastWeather;


public interface WeatherForecastContract {

    interface View extends BaseView<WeatherForecastContract.Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends BasePresenter {
        HourlyForecastWeather getForecastWeather();

        void setView(View view);
    }
}