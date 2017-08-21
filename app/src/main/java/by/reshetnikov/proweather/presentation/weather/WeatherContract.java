package by.reshetnikov.proweather.presentation.weather;


import by.reshetnikov.proweather.presentation.base.PresenterContract;
import by.reshetnikov.proweather.presentation.base.ViewContract;

public interface WeatherContract {
    interface View extends ViewContract<Presenter> {

        void showProgress();

        void hideProgress();

        void showError(String message);

        boolean hasLocationPermissions();

        void requestLocationPermission();

        void startLocationActivity();

        void updateCurrentLocation();

        void showSavedCities();

    }

    interface Presenter extends PresenterContract {
        void getCitiesList();

        void updateLocation();

        void onLocationPermissionsGranted();

        void setView(WeatherContract.View view);
    }
}
