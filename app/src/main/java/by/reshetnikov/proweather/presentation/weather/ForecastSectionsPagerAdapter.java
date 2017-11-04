package by.reshetnikov.proweather.presentation.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.presentation.dailyforecast.DailyForecastFragment;
import by.reshetnikov.proweather.presentation.nowforecast.NowForecastFragment;

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
                return NowForecastFragment.newInstance(position + 1);
            case 1:
                return DailyForecastFragment.newInstance(position + 1);
        }
        return NowForecastFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ProWeatherApp.getAppContext().getString(R.string.now_forecast_page_title);
            case 1:
                return ProWeatherApp.getAppContext().getString(R.string.daily_forecast_page_title);
        }
        return null;
    }
}
