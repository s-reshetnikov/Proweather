package by.reshetnikov.proweather.utils;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;

/**
 * Created by SacRahl on 8/7/2017.
 */

public class CalendarUtil {

    public static int getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        calendar.set(year, month, date);

        long dateInMillis = calendar.getTimeInMillis();
        return (int) dateInMillis / 1000;
    }

    public static String getTime(int time) {
        Context context = ProWeatherApp.getAppContext();
        Date date = new Date(time * 1000L);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.get(Calendar.HOUR) + context.getResources().getString(R.string.short_hour);

    }

    public static int getTodayDateInSeconds() {
        Calendar calendar = Calendar.getInstance();
        long dateInMillis = calendar.getTimeInMillis();
        return (int) dateInMillis / 1000;
    }

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
                return context.getResources().getString(R.string.thursday);
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
