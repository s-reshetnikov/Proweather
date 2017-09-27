package by.reshetnikov.proweather.presentation.location.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.map.MapInteractorContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class MapPresenter implements MapContract.Presenter {

    private MapInteractorContract interactor;
    private SchedulerProvider scheduler;
    private WeakReference<MapContract.View> viewRef;
    private CompositeDisposable disposables;

    @Inject
    public MapPresenter(MapInteractorContract interactor, SchedulerProvider scheduler, CompositeDisposable disposables) {
        this.interactor = interactor;
        this.scheduler = scheduler;
        this.disposables = disposables;
    }

    @Override
    public void start() {
        disposables.clear();
    }

    @Override
    public void stop() {
        disposables.dispose();
    }

    @Override
    public void onMapReady() {
        setupActualLocation();
        setAllLocationsOnMap();
    }

    @Override
    public void onCurrentLocationClicked() {
        if (getView().checkOrRequestLocationPermissions()) {
            disposables.add(interactor.getCurrentLocation()
                    .observeOn(scheduler.ui())
                    .subscribeOn(scheduler.io())
                    .subscribeWith(new DisposableSingleObserver<LocationEntity>() {
                        @Override
                        public void onSuccess(LocationEntity location) {
                            getView().showCurrentLocation(location);
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
    public void cameraMoved() {
        getView().updateCentralMarkerPosition();
    }

    private MapContract.View getView() {
        return viewRef.get();
    }

    @Override
    public void setView(@NonNull MapContract.View view) {
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
                            boolean moveCamera = false;
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
        disposables.add(interactor.getActualLocation()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<LocationEntity>() {
                    @Override
                    public void onSuccess(LocationEntity location) {
                        //boolean moveCamera = true;
                        int zoom = 10;
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        getView().moveCameraToLocation(latLng, zoom);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }
                }));
    }
}
