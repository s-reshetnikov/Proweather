package by.reshetnikov.proweather.weatherForecast;

import by.reshetnikov.proweather.BasePresenter;
import by.reshetnikov.proweather.BaseView;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;


public interface WeatherForecastContract {

    interface View extends BaseView<WeatherForecastContract.Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends BasePresenter {
        ForecastWeather getForecastWeather();

        void setView(View view);
    }
}