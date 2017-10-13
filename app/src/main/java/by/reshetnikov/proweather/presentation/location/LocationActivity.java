package by.reshetnikov.proweather.presentation.location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerFragment;
import by.reshetnikov.proweather.presentation.location.locationmanager.callback.LocationManagerCallback;
import by.reshetnikov.proweather.presentation.location.map.MapCallback;
import by.reshetnikov.proweather.presentation.location.map.MapFragment;
import by.reshetnikov.proweather.utils.FragmentUtils;
import timber.log.Timber;

public class LocationActivity extends AppCompatActivity implements LocationManagerCallback, MapCallback {
    private static final String MAP_FRAGMENT_TAG = "MAP";
    private static final String LOCATION_MANAGER_FRAGMENT_TAG = "location_manager";
    private boolean twoPaneMode = false;
    private LocationManagerFragment locationManagerFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setToolbar();
        locationManagerFragment = (LocationManagerFragment) getSupportFragmentManager().findFragmentByTag(LOCATION_MANAGER_FRAGMENT_TAG);
        if (locationManagerFragment == null)
            locationManagerFragment = new LocationManagerFragment();

        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.location_container, locationManagerFragment, LOCATION_MANAGER_FRAGMENT_TAG);


        if (findViewById(R.id.map_fragment_container) != null) {
            Timber.d("2 panes view");
            twoPaneMode = true;
            mapFragment = new MapFragment();
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.map_fragment_container, mapFragment, MAP_FRAGMENT_TAG);
        } else
            Timber.i("Map fragment container is not present on view");
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.location_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onLocationRemoved() {
        if (twoPaneMode) {
            mapFragment.refreshLocationMarkersOnMap();
        }
    }

    @Override
    public void onLocationAdded(LocationEntity locationEntity) {
        if (twoPaneMode) {
            mapFragment.refreshLocationMarkersOnMap(locationEntity);
        }
    }

    @Override
    public void onLocationsChanged() {
        if (twoPaneMode) {
            mapFragment.refreshLocationMarkersOnMap();
        }
    }

    @Override
    public void onOpenMapClicked() {
        if (twoPaneMode) {
            Timber.w("Button shouldn't be displayed");
        } else {

            mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MAP_FRAGMENT_TAG);

            if (mapFragment == null)
                mapFragment = new MapFragment();

            FragmentUtils.replaceFragmentAndAddToBackStack(getSupportFragmentManager(), R.id.location_container, mapFragment, MAP_FRAGMENT_TAG);
        }

    }

    @Override
    public void onLocationClicked(LocationEntity location) {
        if (twoPaneMode)
            mapFragment.zoomToMarkerRequest(location);
    }
}
