package by.reshetnikov.proweather.presentation.weather;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataManager;


public class WeatherPresenter implements WeatherContract.Presenter {

    DataManager dataManager;

    WeatherContract.View view;

    @Inject
    public WeatherPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void setView(WeatherContract.View view) {
        this.view = view;
    }

    @Override
    public void onLocationPermissionsGranted() {

    }
}
