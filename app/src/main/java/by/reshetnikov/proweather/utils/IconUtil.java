package by.reshetnikov.proweather.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.di.ApplicationContext;

public class IconUtil {
    private Context context;

    public IconUtil(@ApplicationContext Context context) {
        this.context = context;
    }

    public Drawable getIcon(int iconCode, int dateTime) {
        return context.getResources().getDrawable(getSnowCodes(iconCode));
    }

    // 2xx
    public int getThunderstormCodes(int iconCode) {
        return R.drawable.ic_lightning_and_drizzle_cloud;
    }

    // 3xx
    public int getDrizzleCodes(int iconCode) {
        return R.drawable.ic_hard_rain;
    }

    // 5xx
    public int getRainCodes(int iconCode) {
        if (iconCode == 500)
            return R.drawable.ic_rain_and_cloud;
        if (iconCode >= 501 && iconCode <= 504)
            return R.drawable.ic_sun_and_cloud_with_rain;
        if (iconCode == 511)
            return R.drawable.ic_hardrain_cloud;
        if (iconCode >= 520)
            return R.drawable.ic_hard_rain;
        return R.drawable.ic_hard_rain;
    }

    // 6xx
    public int getSnowCodes(int iconCode) {
        return R.drawable.ic_snow_cloud;
    }

    // 7xx
    public int getAtmosphereCodes(int iconCode) {
        return R.drawable.ic_foggy_day;
    }

    // 8xx
    public int getClearCodes(int iconCode) {
        if (iconCode == 800)
            return R.drawable.ic_sunny_day;
        if (iconCode == 801)
            return R.drawable.ic_cloud_with_sun_24dp;
        if (iconCode == 802)
            return R.drawable.ic_cloudy_sky_24dp;
        return R.drawable.ic_cloudy_sky_24dp;
    }
}
