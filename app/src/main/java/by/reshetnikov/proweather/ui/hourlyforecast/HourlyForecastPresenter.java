package by.reshetnikov.proweather.ui.hourlyforecast;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastWeatherApiModel;


public class HourlyForecastPresenter implements HourlyForecastContract.Presenter {

    private HourlyForecastContract.View view;
    private DataRepository dataRepository;

    @Inject
    public HourlyForecastPresenter(DataRepository dataRepository) {
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
    public void setView(HourlyForecastContract.View view) {
        this.view = view;
    }

}
