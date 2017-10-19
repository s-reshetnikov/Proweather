package by.reshetnikov.proweather.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by s-reshetnikov.
 */

public final class PermissionUtils {

    public static boolean isFineLocationGranted(@NonNull Context context) {
        return ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isCoarseLocationGranted(@NonNull Context context) {
        return ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestFineLocationPermission(){

    }

}
