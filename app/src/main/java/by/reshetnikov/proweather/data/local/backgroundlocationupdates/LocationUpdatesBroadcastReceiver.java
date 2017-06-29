package by.reshetnikov.proweather.data.local.backgroundlocationupdates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;

import java.util.List;

import by.reshetnikov.proweather.data.local.preferences.SharedPreferencesStorage;

public class LocationUpdatesBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATES = LocationUpdatesIntentService.class.getPackage()
            + "action.PROCESS_UPDATES";
    private static final String TAG = LocationUpdatesBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
                    SharedPreferencesStorage storage = SharedPreferencesStorage.getInstance(context);
                    storage.setCurrentLocationPreference(locations.get(0));
                }
            }
        }
    }
}
