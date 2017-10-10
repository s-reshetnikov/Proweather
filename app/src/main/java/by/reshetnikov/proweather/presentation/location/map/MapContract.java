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

        void showCancelButton();

        void hideCancelButton();

        boolean checkOrRequestLocationPermissions();

        void showLocationPermissionIsNotGranted();

        void addMarkerOnMap(LocationEntity locationEntity, boolean moveCamera);

        void showCurrentLocation(LocationEntity location);

        void setCurrentLocation(LocationEntity location);

        void moveCameraToLocation(LatLng coordinates, int zoom);

        void showLocationPointer();

        void hideLocationPointer();

        void updateFabWithCheckIcon();

        void updateFabWithAddIcon();

        void updateLocations();

        void updateLocations(LocationEntity locationEntity);
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

        void updateLocations();

        void updateLocations(LocationEntity location);
    }
}
