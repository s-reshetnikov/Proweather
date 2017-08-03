package by.reshetnikov.proweather.ui.weather;


import by.reshetnikov.proweather.ui.base.PresenterContract;
import by.reshetnikov.proweather.ui.base.ViewContract;

public interface WeatherContract {
    interface View extends ViewContract<Presenter> {

        void showProgress();

        void hideProgress();

        void showError(String message);

        boolean hasLocationPermissions();

        void requestLocationPermission();

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
