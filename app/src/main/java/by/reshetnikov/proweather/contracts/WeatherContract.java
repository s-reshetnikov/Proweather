package by.reshetnikov.proweather.contracts;


public interface WeatherContract {
    interface View extends BaseView<Presenter> {

        void showProgress();

        void hideProgress();

        void showError(String message);

        boolean hasLocationPermissions();

        void requestLocationPermission();

        void updateCurrentLocation();

        void showSavedCities();

    }

    interface Presenter extends BasePresenter {
        void getCitiesList();

        void updateLocation();

        void onLocationPermissionsGranted();

        void setView(WeatherContract.View view);
    }
}
