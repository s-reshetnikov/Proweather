package by.reshetnikov.proweather.utils;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import by.reshetnikov.proweather.ProWeatherApp;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by s-reshetnikov.
 */

public final class PermissionUtils {

    public static boolean isFineLocationGranted() {
        return ActivityCompat.checkSelfPermission(ProWeatherApp.getAppContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isCoarseLocationGranted() {
        return ActivityCompat.checkSelfPermission(ProWeatherApp.getAppContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
