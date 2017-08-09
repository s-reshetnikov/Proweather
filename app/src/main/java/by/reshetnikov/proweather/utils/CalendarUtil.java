package by.reshetnikov.proweather.utils;

import android.content.Context;

import java.util.Calendar;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;

/**
 * Created by SacRahl on 8/7/2017.
 */

public class CalendarUtil {

    public static String getWeekDay() {
        Context context = ProWeatherApp.getAppContext();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                return context.getResources().getString(R.string.monday);
            case Calendar.TUESDAY:
                return context.getResources().getString(R.string.tuesday);
            case Calendar.WEDNESDAY:
                return context.getResources().getString(R.string.wednesday);
            case Calendar.THURSDAY:
                return context.getResources().getString(R.string.thursaday);
            case Calendar.FRIDAY:
                return context.getResources().getString(R.string.friday);
            case Calendar.SATURDAY:
                return context.getResources().getString(R.string.saturday);
            case Calendar.SUNDAY:
                return context.getResources().getString(R.string.sunday);
        }
        return "";
    }
}
