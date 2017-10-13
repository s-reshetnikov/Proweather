package by.reshetnikov.proweather.presentation.location.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.di.component.ActivityComponent;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.utils.PermissionUtils;
import by.reshetnikov.proweather.utils.ToastUtils;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class MapFragment extends Fragment implements MapContract.View, MapFragmentCommunication, OnMapReadyCallback {
    //GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_PERMISSION_LOCATION = 14121;
    private final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    @Inject
    MapContract.Presenter presenter;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.ivLocationPointer)
    ImageView ivLocationPointer;
    @BindView(R.id.fab_add_location_to_list)
    FloatingActionButton fabCurrentLocation;
    @BindView(R.id.fab_cancel_add_location_to_list)
    FloatingActionButton fabCancelAddLocation;
    private GoogleMap map;
    private ActivityComponent component;
    private List<Marker> mapMarkers;

    public MapFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            component = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule((AppCompatActivity) context))
                    .applicationComponent(((ProWeatherApp) getActivity().getApplication()).getComponent())
                    .build();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        component.inject(this);
        ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        MapsInitializer.initialize(this.getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        mapMarkers = new ArrayList<>();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        if (mapView != null)
            mapView.onPause();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null)
            mapView.onLowMemory();
    }

    @Override
    public void onStop() {
        if (mapView != null)
            mapView.onStop();
        presenter.stop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mapView != null)
            mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this.getContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                    }

                } else {
                    presenter.locationPermissionsDenied();
                }
                return;
            }
        }
        map.setMyLocationEnabled(true);
    }

    @OnClick(R.id.fab_add_location_to_list)
    public void addNewLocation() {
        boolean isPointerVisible = ivLocationPointer.getVisibility() == View.VISIBLE;
        LatLng coordinates = map.getCameraPosition().target;
        presenter.onAddNewLocationButtonClicked(isPointerVisible, coordinates.latitude, coordinates.longitude);
    }

    @OnClick(R.id.fab_cancel_add_location_to_list)
    public void cancelAddNewLocation() {
        presenter.onCancelButtonClicked();
    }

    @Override
    public void showCancelButton() {
        fabCancelAddLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCancelButton() {
        fabCancelAddLocation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null)
            return;

        map = googleMap;
        setUpMap();
        presenter.onMapReady();
    }

    @Override
    public boolean checkOrRequestLocationPermissions() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        return checkLocationPermission();
    }

    @Override
    public void showLocationPermissionIsNotGranted() {
        ToastUtils.showToast(Toast.makeText(this.getContext(), "Permissions is not granted", Toast.LENGTH_SHORT));
    }

    @Override
    public void addMarkerOnMap(LocationEntity locationEntity, boolean moveCamera) {
        LatLng location = new LatLng(locationEntity.getLatitude(), locationEntity.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .title(locationEntity.getLocationName());

        mapMarkers.add(map.addMarker(markerOptions));
        if (moveCamera) {
            int zoom = 10;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        }
    }

    @Override
    public void moveCameraToCoordinates(double latitude, double longitude, int zoom) {
        LatLng coordinates = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
    }

    @Override
    public void showLocationPointer() {
        ivLocationPointer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLocationPointer() {
        ivLocationPointer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateFabWithCheckIcon() {
        fabCurrentLocation.setImageResource(R.drawable.ic_check_24dp);
    }

    @Override
    public void updateFabWithAddIcon() {
        fabCurrentLocation.setImageResource(R.drawable.ic_add_24dp);
    }

    @Override
    public void refreshLocationMarkersOnMap() {
        presenter.updateLocationMarkers();
    }


    @Override
    public void refreshLocationMarkersOnMap(LocationEntity locationEntity) {
        presenter.updateLocationMarkersWithZoom(locationEntity);
    }

    @Override
    public void clearAllMapMarkers() {
        clearMarkers();
    }

    private void clearMarkers() {
        for (Marker marker : mapMarkers)
            marker.remove();
    }

    // check performed at @class PermissionUtils
    @SuppressLint("MissingPermission")
    private void setUpMap() {
        if (PermissionUtils.isCoarseLocationGranted(this.getActivity()) || PermissionUtils.isFineLocationGranted(this.getActivity())) {
            Timber.d("location permissions denied");
            return;
        }
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(MapFragment.this.getContext(), "On my location button clicked", Toast.LENGTH_SHORT).show();
                presenter.onCurrentLocationButtonClicked();
                return false;
            }
        });
    }

    private boolean checkLocationPermission() {
        if (PermissionUtils.isCoarseLocationGranted(this.getActivity()) && PermissionUtils.isFineLocationGranted(this.getActivity())) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), ACCESS_COARSE_LOCATION)) {

                new AlertDialog.Builder(this.getContext())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapFragment.this.getActivity(),
                                        new String[]{ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    private void checkPlayServicesAvailable() {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int status = apiAvailability.isGooglePlayServicesAvailable(this.getContext());

        if (status != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(status)) {
                apiAvailability.getErrorDialog(this.getActivity(), status, 1).show();
            } else {
                Snackbar.make(mapView, "Google Play Services unavailable. This app will not work", Snackbar.LENGTH_INDEFINITE).show();
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage(getString(R.string.gps_disabled_quastion_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog noGpsAlert = builder.create();
        noGpsAlert.show();
    }

    @Override
    public void updateLocationMarkersRequest() {
        presenter.updateLocationMarkers();
    }

    @Override
    public void updateLocationMarkersWithZoomRequest(LocationEntity location) {
        presenter.updateLocationMarkersWithZoom(location);
    }

    @Override
    public void zoomToMarkerRequest(LocationEntity location) {
        presenter.zoomToMarker(location);
    }
}
