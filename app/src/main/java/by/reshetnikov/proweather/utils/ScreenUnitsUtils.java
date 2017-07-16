package by.reshetnikov.proweather.utils;

import android.content.res.Resources;

public class ScreenUnitsUtils {
    public static int convertDpToPixel(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int convertPixelToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
