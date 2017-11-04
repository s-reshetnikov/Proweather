package by.reshetnikov.proweather.presentation.location.map;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.map.MapInteractorContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class MapPresenter implements MapContract.Presenter {

    private MapInteractorContract interactor;
    private ThreadSchedulerProvider scheduler;
    private WeakReference<MapContract.View> viewRef;
    private CompositeDisposable disposables;

    @Inject
    public MapPresenter(MapInteractorContract interactor, ThreadSchedulerProvider scheduler, CompositeDisposable disposables) {
        this.interactor = interactor;
        this.scheduler = scheduler;
        this.disposables = disposables;
    }

    @Override
    public void start() {
        //empty
    }

    @Override
    public void stop() {
        disposables.clear();
    }

    @Override
    public void onMapReady() {
        Timber.d("onMapReady()");
        setupActualLocation();
        setAllLocationsOnMap();
    }

    @Override
    public void onAddNewLocationButtonClicked(boolean isPointerVisible, double latitude, double longitude) {
        Timber.d("onAddNewLocationButtonClicked()");
        if (!isPointerVisible) {
            getView().showLocationPointer();
            getView().updateFabWithCheckIcon();
            getView().showCancelButton();
        } else {
            disposables.add(interactor.getLocationByCoordinates(latitude, longitude)
                    .observeOn(scheduler.ui())
                    .doAfterTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            getView().hideCancelButton();
                            getView().hideLocationPointer();
                            getView().updateFabWithAddIcon();
                        }
                    })
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribeWith(new DisposableSingleObserver<LocationEntity>() {
                        @Override
                        public void onSuccess(LocationEntity locationEntity) {
                            getView().clearAllMapMarkers();
                            getView().addMarkerOnMap(locationEntity, false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e);
                        }
                    }));
        }
    }

    @Override
    public void onCurrentLocationButtonClicked() {
        Timber.d("onCurrentLocationButtonClicked()");
        if (interactor.canUseCurrentLocation() && getView().checkOrRequestLocationPermissions()) {
            disposables.add(interactor.getCurrentLocation()
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribeWith(new DisposableSingleObserver<LocationEntity>() {
                        @Override
                        public void onSuccess(LocationEntity location) {
                            //getView().showCurrentLocation(location);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e);
                        }
                    }));
        } else {
            getView().showLocationPermissionIsNotGranted();
        }
    }

    @Override
    public void playServicesNotInstalled() {
        Timber.e("Google play services not installed");
    }

    @Override
    public void onCancelButtonClicked() {
        Timber.d("onCancelButtonClicked()");
        getView().hideCancelButton();
        getView().hideLocationPointer();
        getView().updateFabWithAddIcon();
    }

    @Override
    public void updateLocationMarkers() {
        Timber.d("updateLocationMarkers()");
        getView().clearAllMapMarkers();
        setAllLocationsOnMap();
    }

    @Override
    public void updateLocationMarkersWithZoom(LocationEntity location) {
        Timber.d("updateLocationMarkersWithZoom()");
        getView().refreshLocationMarkersOnMap();
        int zoomScale = 14;
        getView().moveCameraToCoordinates(location.getLatitude(), location.getLongitude(), zoomScale);
    }

    @Override
    public void zoomToMarker(LocationEntity location) {
        Timber.d("zoomToMarker()");
        int zoomScale = 14;
        getView().moveCameraToCoordinates(location.getLatitude(), location.getLongitude(), zoomScale);
    }

    @Override
    public void locationPermissionsDenied() {
        Timber.d("locationPermissionsDenied()");
        getView().showLocationPermissionIsNotGranted();
        getView().checkOrRequestLocationPermissions();
    }

    private MapContract.View getView() {
        return viewRef.get();
    }

    @Override
    public void setView(MapContract.View view) {
        viewRef = new WeakReference<>(view);
    }

    private void setAllLocationsOnMap() {
        disposables.add(interactor.getAllSavedLocations()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<List<LocationEntity>>() {
                    @Override
                    public void onSuccess(List<LocationEntity> locations) {
                        for (LocationEntity location : locations) {
                            // move view to default location
                            boolean moveCamera = location.getPosition() == 0;
                            getView().addMarkerOnMap(location, moveCamera);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }
                }));

    }

    private void setupActualLocation() {
        disposables.add(interactor.getCurrentLocation()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<LocationEntity>() {
                    @Override
                    public void onSuccess(LocationEntity location) {
                        int zoomScale = 10;
                        getView().moveCameraToCoordinates(location.getLatitude(), location.getLongitude(), zoomScale);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }
                }));
    }
}
