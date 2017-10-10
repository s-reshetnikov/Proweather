package by.reshetnikov.proweather.presentation.location;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerCommunication;
import by.reshetnikov.proweather.presentation.location.locationmanager.callback.LocationManagerCallback;
import by.reshetnikov.proweather.presentation.location.map.MapCallback;
import by.reshetnikov.proweather.presentation.location.map.MapFragment;
import by.reshetnikov.proweather.presentation.location.map.MapFragmentCommunication;
import by.reshetnikov.proweather.utils.FragmentUtils;
import timber.log.Timber;

public class LocationActivity extends AppCompatActivity implements LocationManagerCallback, MapCallback {

    private boolean twoPaneMode = false;
    private LocationManagerCommunication locationManagerCommunication;
    private MapFragmentCommunication mapCommunication;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setToolbar();
        locationManagerCommunication = (LocationManagerCommunication) getSupportFragmentManager().findFragmentById(R.id.fragment_location_manager);

        if (findViewById(R.id.map_fragment_container) != null) {
            Timber.d("2 panes view");
            twoPaneMode = true;
            mapFragment = new MapFragment();
            mapCommunication = mapFragment;
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.map_fragment_container, mapFragment);
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
            mapFragment.updateLocations();
        }
    }

    @Override
    public void onLocationAdded(LocationEntity locationEntity) {
        if (twoPaneMode) {
            mapFragment.updateLocations(locationEntity);
        }
    }

    @Override
    public void onLocationsChanged() {
        if (twoPaneMode) {
            mapFragment.updateLocations();
        }
    }

    @Override
    public void onOpenMapClicked() {
        if (twoPaneMode) {
            Timber.d("Button shouldn't be there");
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_location_manager, new MapFragment(), "map");
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
