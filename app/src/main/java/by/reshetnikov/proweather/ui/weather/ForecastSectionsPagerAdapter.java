package by.reshetnikov.proweather.ui.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import by.reshetnikov.proweather.ui.currentforecast.WeatherNowFragment;
import by.reshetnikov.proweather.ui.forecast.ForecastFragment;

/**
 * Created by SacRahl on 8/3/2017.
 */

public class ForecastSectionsPagerAdapter extends FragmentPagerAdapter {
    public ForecastSectionsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WeatherNowFragment.newInstance(position + 1);
            case 1:
                return ForecastFragment.newInstance(position + 1);
        }
        return WeatherNowFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "NOW";
            case 1:
                return "FORECAST";
        }
        return null;
    }
}
