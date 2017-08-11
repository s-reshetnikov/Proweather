package by.reshetnikov.proweather.ui.weather;

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
    public void getCitiesList() {

    }

    @Override
    public void updateLocation() {
//        if (repository.canUseCurrentLocation()) {
        if (!view.hasLocationPermissions()) {
            view.requestLocationPermission();
        }
        if (view.hasLocationPermissions()) {
            view.updateCurrentLocation();
        }
//        }
    }

    @Override
    public void onLocationPermissionsGranted() {
//        repository.getCurrentLocation();
//        view.updateCurrentLocation();
    }


    @Override
    public void setView(WeatherContract.View view) {
        this.view = view;
    }
}
