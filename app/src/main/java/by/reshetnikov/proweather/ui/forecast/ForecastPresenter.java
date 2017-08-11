package by.reshetnikov.proweather.ui.forecast;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataManager;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;


public class ForecastPresenter implements ForecastContract.Presenter {

    private ForecastContract.View view;
    private DataManager dataManager;

    @Inject
    public ForecastPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    @Override
    public HourlyForecastApiModel getForecastWeather() {
        return null;
    }

    @Override
    public void setView(ForecastContract.View view) {
        this.view = view;
    }

}
