package by.reshetnikov.proweather.weatherForecast;

import by.reshetnikov.proweather.BasePresenter;
import by.reshetnikov.proweather.BaseView;
import by.reshetnikov.proweather.currentweather.CurrentWeatherContract;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;


public interface WeatherForecastContract {

    interface View extends BaseView<CurrentWeatherContract.CurrentWeatherPresenter> {

        void showWeatherForecast();
    }

    interface WeatherForecastPresenter extends BasePresenter {
        ForecastWeather getForecastWeather(String cityId);
    }
}
