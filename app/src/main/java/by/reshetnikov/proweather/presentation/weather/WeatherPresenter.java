package by.reshetnikov.proweather.presentation.weather;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.weather.WeatherInteractorContract;
import io.reactivex.disposables.CompositeDisposable;


public class WeatherPresenter implements WeatherContract.Presenter {

    private WeakReference<WeatherContract.View> viewRef;
    private WeatherInteractorContract interactor;
    private CompositeDisposable disposables;

    @Inject
    WeatherPresenter(WeatherInteractorContract interactor, CompositeDisposable disposables) {
        this.interactor = interactor;
        this.disposables = disposables;
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {
        tryToStartLocationService();
        getView().startNowForecastService();
    }

    private void tryToStartLocationService() {
        if (interactor.canUseCurrentLocation())

            if (getView().hasLocationPermissions())
                getView().startLocationService();
            else
                getView().requestLocationPermission();
    }

    @Override
    public void onLocationPermissionsResult(boolean allowed) {
        if (allowed) {
            tryToStartLocationService();
        } else
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
