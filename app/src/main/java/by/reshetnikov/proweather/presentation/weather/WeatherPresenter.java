package by.reshetnikov.proweather.presentation.weather;

import java.lang.ref.WeakReference;

import javax.inject.Inject;


public class WeatherPresenter implements WeatherContract.Presenter {

    private WeakReference<WeatherContract.View> viewRef;

    @Inject
    WeatherPresenter() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void onLocationPermissionsResult(boolean allowed) {
        if (allowed)
            getView().requestLocationPermission();
        else
            getView().showPermissionDenied();
    }

    @Override
    public void onSettingsClicked() {
        getView().startSettingsActivity();
    }

    @Override
    public void onManageLocationsClicked() {
        getView().startLocationActivity();
    }

    @Override
    public void onOpenAppPermissionsClicked() {
        getView().showGrantPermissionsInSettingsMessage();
        getView().openApplicationSettings();
    }

    private WeatherContract.View getView() {
        if (viewRef != null)
            return viewRef.get();
        throw new NullPointerException("View is null");
    }

    @Override
    public void setView(WeatherContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

}
