package by.reshetnikov.proweather.data.service.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;

import java.util.List;

/**
 * Created by s-reshetnikov.
 */

public class LocationIntentService extends IntentService {

    static final String ACTION_PROCESS_UPDATES =
            "com.google.android.gms.location.sample.backgroundlocationupdates.action" +
                    ".PROCESS_UPDATES";

    public LocationIntentService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
//                    LocationResultHelper locationResultHelper = new LocationResultHelper(this, locations);
                    // Save the location data to SharedPreferences.
//                    locationResultHelper.saveResults();
                    // Show notification with the location data.
//                    locationResultHelper.showNotification();
//                    Timber.i(LocationResultHelper.getSavedLocationResult(this));
                }
            }
        }
    }
}
