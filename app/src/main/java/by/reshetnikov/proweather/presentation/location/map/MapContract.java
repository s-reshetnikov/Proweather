package by.reshetnikov.proweather.presentation.location.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.base.PresenterContract;

/**
 * Created by s-reshetnikov.
 */

public interface MapContract {

    interface View {

        boolean checkOrRequestLocationPermissions();

        void showLocationPermissionIsNotGranted();

        void addMarkerOnMap(LocationEntity locationEntity, boolean moveCamera);

        void showCurrentLocation(LocationEntity location);

        void setCurrentLocation(LocationEntity location);

        void moveCameraToLocation(LatLng coordinates, int zoom);

        void updateCentralMarkerPosition();
    }

    interface Presenter extends PresenterContract {

        void start();

        void stop();

        void setView(@NonNull MapContract.View view);

        void onMapReady();

        void onCurrentLocationClicked();

        void playServicesNotInstalled();

        void cameraMoved();
    }
}
