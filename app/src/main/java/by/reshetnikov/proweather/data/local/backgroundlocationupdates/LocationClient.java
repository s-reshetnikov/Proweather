package by.reshetnikov.proweather.data.local.backgroundlocationupdates;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationClient implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LocationClient.class.getSimpleName();
    private static final int UPDATE_INTERVAL = 60 * 60 * 1000;
    private static final int FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL * 2 / 3;
    private static final int MAX_WAIT_TIME = 30 * 1000;

    private static final int REQUEST_CODE = 9140;

    private Context context;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    LocationClient() {
        context = this.context;
    }

    private void buildGoogleApiClient() {
        if (googleApiClient != null) {
            return;
        }
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .enableAutoManage((FragmentActivity) context, this)
                .addApi(LocationServices.API)
                .build();
    }

    public void createLocationRequest() {
        if (googleApiClient == null)
            return;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }

    public boolean requestLocationUpdates() {
        try {
            Log.i(TAG, "Starting location updates");
            //LocationRequestUtils.setRequesting(context, true);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, getPendingIntent());
        } catch (SecurityException e) {
            //LocationRequestUtils.setRequesting(context, false);
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public void removeLocationUpdates() {
        Log.i(TAG, "Removing location updates");
        //LocationRequestUtils.setRequesting(context, false);
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,
                getPendingIntent());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "GoogleApiClient connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        final String text = "Connection suspended";
        Log.w(TAG, text + ": Error code: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        final String text = "Exception while connecting to Google Play services";
        Log.w(TAG, text + ": " + connectionResult.getErrorMessage());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(context, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
