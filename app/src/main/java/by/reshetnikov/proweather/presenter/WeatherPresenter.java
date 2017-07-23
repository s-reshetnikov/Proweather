package by.reshetnikov.proweather.presenter;

import javax.inject.Inject;

import by.reshetnikov.proweather.contract.WeatherContract;
import by.reshetnikov.proweather.data.DataRepository;


public class WeatherPresenter implements WeatherContract.Presenter {

    DataRepository dataRepository;

    WeatherContract.View view;

    @Inject
    public WeatherPresenter(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
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
