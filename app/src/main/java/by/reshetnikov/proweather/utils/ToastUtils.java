package by.reshetnikov.proweather.utils;

import android.widget.Toast;

import by.reshetnikov.proweather.ProWeatherApp;

/**
 * Created by SacRahl on 6/8/2017.
 */

public final class ToastUtils {

    private static Toast currentToast;

    public static void showToast(Toast toast) {
        if (currentToast != null)
            currentToast.cancel();
        currentToast = toast;
        currentToast.show();
    }

    public static void showTurnInternetOnToast() {
        Toast toast = Toast.makeText(ProWeatherApp.getAppContext(), "Turn on internet to get weather forecast", Toast.LENGTH_LONG);
        ToastUtils.showToast(toast);
    }
}
