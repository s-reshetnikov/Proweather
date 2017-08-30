package by.reshetnikov.proweather.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;

/**
 * Created by SacRahl on 8/7/2017.
 */

public class CalendarUtil {

    public static long getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        calendar.set(year, month, date);

        long dateInMillis = calendar.getTimeInMillis();
        return dateInMillis / 1000;
    }

    public static String getTimeInHours(long time) {
        Calendar calendar = getCalendarForTime(time);
        Context context = ProWeatherApp.getAppContext();
        return calendar.get(Calendar.HOUR) + context.getResources().getString(R.string.short_hour);

    }

    public static int getTodayDateInSeconds() {
        Calendar calendar = Calendar.getInstance();
        long dateInMillis = calendar.getTimeInMillis();
        return (int) dateInMillis / 1000;
    }

    public static String getWeekDay(long date) {
        Context context = ProWeatherApp.getAppContext();
        Calendar calendar = getCalendarForTime(date);
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

    public static String getTodayWeekDay() {
        return getWeekDay(getTodayDate());
    }

    public static String getLocaleDayAndMonth(long dateInSeconds) {
        Date date = new Date(dateInSeconds * 1000);
        String pattern = "d MMMM";
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public static int getDateWithoutTime(long dateTime) {
        Calendar calendar = getCalendarForTime(dateTime);
        removeTimeFromCalendar(calendar);
        return (int) calendar.getTimeInMillis() / 1000;
    }

    private static void removeTimeFromCalendar(Calendar calendar) {
        calendar.clear(Calendar.HOUR_OF_DAY);
        calendar.clear(Calendar.AM_PM);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
    }

    @NonNull
    private static Calendar getCalendarForTime(long dateTime) {
        Date date = new Date(dateTime * 1000L);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar;
    }

    public static long getDateFromToday(int numOfDays) {
        Calendar calendar = Calendar.getInstance();
        removeTimeFromCalendar(calendar);
        int secondsInDay = 60 * 60 * 24;
        int secondsInNDays = secondsInDay * numOfDays;
        return calendar.getTimeInMillis() / 1000 + secondsInNDays;
    }
}