package by.reshetnikov.proweather.presentation.weather;


import by.reshetnikov.proweather.presentation.base.PresenterContract;

public interface WeatherContract {

    interface View {

        void startSettingsActivity();

        boolean hasLocationPermissions();

        void requestLocationPermission();

        void startLocationActivity();

        void showPermissionDenied();

        void openApplicationSettings();

        void showGrantPermissionsInSettingsMessage();

        void startNowForecastService();

        void startNowForecastServiceImmediate();

        void startLocationService();

        boolean checkPlayServicesAvailable();

        void tryToResolveGooglePlayServiceAvailabilityError();
    }

    interface Presenter extends PresenterContract {

        void setView(WeatherContract.View view);

        void create();

        void onLocationPermissionsResult(boolean allowed);

        void onSettingsClicked();

        void onManageLocationsClicked();

        void onOpenAppPermissionsClicked();
    }
}
