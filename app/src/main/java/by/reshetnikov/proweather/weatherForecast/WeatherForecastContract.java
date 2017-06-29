package by.reshetnikov.proweather.weatherForecast;

import by.reshetnikov.proweather.BasePresenter;
import by.reshetnikov.proweather.BaseView;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.HourlyForecastWeather;


public interface WeatherForecastContract {

    interface View extends BaseView<WeatherForecastContract.Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends BasePresenter {
        HourlyForecastWeather getForecastWeather();

        void setView(View view);
    }
}