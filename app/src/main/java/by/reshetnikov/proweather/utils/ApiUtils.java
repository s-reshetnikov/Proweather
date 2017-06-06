package by.reshetnikov.proweather.utils;

import by.reshetnikov.proweather.BuildConfig;

/**
 * Created by SacRahl on 6/6/2017.
 */

public final class ApiUtils {

    public static final String CITY_NAME_PARAM = "q";
    public static final String CITY_ID_PARAM = "id";
    public static final String CITY_LONGITUDE_PARAM = "lat";
    public static final String CITY_LATITUDE_PARAM = "lon";
    public static final String CITY_ZIP_CODE_PARAM = "zip";
    public static final String UNITS_PARAM = "units";
    public static final String LANGUAGE_PARAM = "lang";
    public static final String API_KEY_PARAM = "appId";

    public static String getApiKey() {
        return BuildConfig.OWM_API_KEY;
    }
}
