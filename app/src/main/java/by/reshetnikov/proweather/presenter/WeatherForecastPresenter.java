package by.reshetnikov.proweather.presenter;

import by.reshetnikov.proweather.contract.WeatherForecastContract;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;


public class WeatherForecastPresenter implements WeatherForecastContract.Presenter {

    private WeatherForecastContract.View view;

    public WeatherForecastPresenter() {
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    @Override
    public HourlyForecastWeatherApiModel getForecastWeather() {
        return null;
    }

    @Override
    public void setView(WeatherForecastContract.View view) {
        this.view = view;
    }

}
