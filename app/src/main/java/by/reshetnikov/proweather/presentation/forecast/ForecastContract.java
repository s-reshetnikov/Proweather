package by.reshetnikov.proweather.presentation.forecast;

import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.presentation.base.PresenterContract;
import by.reshetnikov.proweather.presentation.base.ViewContract;


public interface ForecastContract {

    interface View extends ViewContract<Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends PresenterContract {
        HourlyForecastApiModel getForecastWeather();

        void setView(View view);
    }
}