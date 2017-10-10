package by.reshetnikov.proweather.presentation.weather;


import by.reshetnikov.proweather.presentation.base.PresenterContract;

public interface WeatherContract {
    interface View {
        void startLocationActivity();

    }

    interface Presenter extends PresenterContract {

        void setView(WeatherContract.View view);

        void onLocationPermissionsGranted();
    }
}
