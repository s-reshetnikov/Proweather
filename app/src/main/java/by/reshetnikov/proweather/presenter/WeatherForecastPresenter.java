package by.reshetnikov.proweather.presenter;

import javax.inject.Inject;

import by.reshetnikov.proweather.contract.WeatherForecastContract;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.network.model.forecastweather.HourlyForecastWeatherApiModel;


public class WeatherForecastPresenter implements WeatherForecastContract.Presenter {

    private WeatherForecastContract.View view;
    private DataRepository dataRepository;

    @Inject
    public WeatherForecastPresenter(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
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
