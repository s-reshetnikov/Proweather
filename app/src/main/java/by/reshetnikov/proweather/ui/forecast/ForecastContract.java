package by.reshetnikov.proweather.ui.forecast;

import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.ui.base.PresenterContract;
import by.reshetnikov.proweather.ui.base.ViewContract;


public interface ForecastContract {

    interface View extends ViewContract<Presenter> {

        void showWeatherForecast();
    }

    interface Presenter extends PresenterContract {
        HourlyForecastApiModel getForecastWeather();

        void setView(View view);
    }
}