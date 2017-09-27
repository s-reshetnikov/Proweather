package by.reshetnikov.proweather.presentation.location.map;

import android.Manifest;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import by.reshetnikov.proweather.utils.ToastUtils;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback {
    //GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_PERMISSION_LOCATION = 14121;
    //private LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    private final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    @Inject
    MapContract.Presenter presenter;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.fab_add_location_to_list)
    FloatingActionButton btnCurrentLocation;
    private GoogleMap map;
    private ActivityComponent component;

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
                    // permission denied
                }
                return;
            }
        }
        map.setMyLocationEnabled(true);
    }

    @OnClick(R.id.fab_add_location_to_list)
    public void findCurrentLocation() {
        presenter.onCurrentLocationClicked();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null)
            return;

        map = googleMap;
        presenter.onMapReady();
        setUpMap();
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
//        // Add a marker in Sydney, Australia, and move the camera.
        LatLng location = new LatLng(locationEntity.getLatitude(), locationEntity.getLongitude());
        map.addMarker(new MarkerOptions().position(location).title(locationEntity.getLocationName()));
        if (moveCamera) {
            int zoom = 10;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        }
    }


    @Override
    public void showCurrentLocation(LocationEntity location) {

    }

    @Override
    public void setCurrentLocation(LocationEntity location) {

    }

    @Override
    public void moveCameraToLocation(LatLng coordinates, int zoom) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
    }

    @Override
    public void updateCentralMarkerPosition() {
        LatLng coordinates = map.getCameraPosition().target;

    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Timber.i("location permissions denied");
            return;
        }
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(MapFragment.this.getContext(), "On my location button clicked", Toast.LENGTH_SHORT).show();
                presenter.onCurrentLocationClicked();
                return false;
            }
        });
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                presenter.cameraMoved();
            }
        });
        MarkerOptions markerOptions = new MarkerOptions()
                .position(map.getCameraPosition().target)
                .title("Title")
                .snippet("Snippet")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location));
        map.addMarker(markerOptions);
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
                // No explanation needed, we can request the permission.
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
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
