package by.reshetnikov.proweather.presentation.location.map;

import android.support.annotation.NonNull;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.base.PresenterContract;

/**
 * Created by s-reshetnikov.
 */

public interface MapContract {

    interface View {

        void showCancelButton();

        void hideCancelButton();

        boolean checkOrRequestLocationPermissions();

        void showLocationPermissionIsNotGranted();

        void addMarkerOnMap(LocationEntity locationEntity, boolean moveCamera);

        void moveCameraToCoordinates(double latitude, double longitude, int zoom);

        void showLocationPointer();

        void hideLocationPointer();

        void updateFabWithCheckIcon();

        void updateFabWithAddIcon();

        void refreshLocationMarkersOnMap();

        void refreshLocationMarkersOnMap(LocationEntity locationEntity);

        void clearAllMapMarkers();
    }

    interface Presenter extends PresenterContract {

        void start();

        void stop();

        void setView(@NonNull MapContract.View view);

        void onMapReady();

        void onAddNewLocationButtonClicked(boolean isPointerVisible, double latitude, double longitude);

        void onCurrentLocationButtonClicked();

        void playServicesNotInstalled();

        void onCancelButtonClicked();

        void updateLocationMarkers();

        void updateLocationMarkersWithZoom(LocationEntity location);

        void zoomToMarker(LocationEntity location);

        void locationPermissionsDenied();
    }
}
