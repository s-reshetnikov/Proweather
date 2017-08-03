package by.reshetnikov.proweather.ui.hourlyforecast;

import by.reshetnikov.proweather.ui.base.PresenterContract;
import by.reshetnikov.proweather.ui.base.ViewContract;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;


public interface HourlyForecastContract {

    interface View extends ViewContract<Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends PresenterContract {
        HourlyForecastWeatherApiModel getForecastWeather();

        void setView(View view);
    }
}