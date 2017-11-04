package by.reshetnikov.proweather.utils;


import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;

public final class AppConstants {

    public static final String DB_NAME = ProWeatherApp.getAppContext().getResources().getString(R.string.db_name);

    public static final String OPEN_WEATHER_API_URL = ProWeatherApp.getAppContext().getResources().getString(R.string.open_weather_map_api_url);

    // in seconds
    public static final int CONNECTION_TIME_OUT = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.internet_connection_timeout);

    // in seconds
    public static final int LOCATION_SERVICE_WINDOW_START = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.location_service_window_start);
    public static final int LOCATION_SERVICE_WINDOW_END = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.location_service_window_end);

    // in seconds
    public static final int FORECAST_SERVICE_WINDOW_START = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.forecast_service_window_start);
    public static final int FORECAST_SERVICE_WINDOW_END = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.forecast_service_window_end);

    // in millis
    public static final int LOCATION_LOW_POWER_FASTEST_INTERVAL = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.location_low_power_fastest_interval);
    public static final int LOCATION_LOW_POWER_SLOWEST_INTERVAL = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.location_low_power_slowest_interval);

    // in millis
    public static final int LOCATION_BALANCED_POWER_FASTEST_INTERVAL = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.location_balanced_power_fastest_interval);
    public static final int LOCATION_BALANCED_POWER_SLOWEST_INTERVAL = ProWeatherApp.getAppContext().getResources().getInteger(R.integer.location_balanced_power_slowest_interval);

}
