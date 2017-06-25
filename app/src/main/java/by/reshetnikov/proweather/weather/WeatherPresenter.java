package by.reshetnikov.proweather.weather;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataRepository;


public class WeatherPresenter implements WeatherContract.Presenter {

    @Inject
    DataRepository repository;

    WeatherContract.View view;

    WeatherPresenter() {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onError() {

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
        repository.getCurrentLocation();
        view.updateCurrentLocation();
    }


    @Override
    public void setView(WeatherContract.View view) {
        this.view = view;
    }
}
