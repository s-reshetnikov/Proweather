package by.reshetnikov.proweather.data.local.backgroundlocationupdates;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

import java.util.List;

import by.reshetnikov.proweather.data.local.preferences.SharedPreferencesStorage;


public class LocationUpdatesIntentService extends IntentService {

    private static final String TAG = LocationUpdatesIntentService.class.getSimpleName();


    public LocationUpdatesIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
                    SharedPreferencesStorage storage = SharedPreferencesStorage.getInstance();
                    if (storage != null) {
                        storage.setCurrentLocationPreference(locations.get(0));
//                        Log.i(TAG, storage.getLocation().toString());
                    } else {
                        Log.i(TAG, "Storage has no context yet");
                    }
                }
            }
        }
    }
}
