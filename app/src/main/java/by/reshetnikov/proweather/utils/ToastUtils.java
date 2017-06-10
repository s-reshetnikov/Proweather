package by.reshetnikov.proweather.utils;

import android.widget.Toast;

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
}
