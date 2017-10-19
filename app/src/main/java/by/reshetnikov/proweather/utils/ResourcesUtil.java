package by.reshetnikov.proweather.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import by.reshetnikov.proweather.ProWeatherApp;

/**
 * Created by s-reshetnikov.
 */

public final class ResourcesUtil {

    @ColorInt
    public static int getColor(@ColorRes int color) {
        return ContextCompat.getColor(ProWeatherApp.getAppContext(), color);
    }

    public static Drawable getDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(ProWeatherApp.getAppContext(), drawable);
    }
}
