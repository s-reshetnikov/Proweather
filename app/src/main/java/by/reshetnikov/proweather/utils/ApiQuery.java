package by.reshetnikov.proweather.utils;

import java.util.HashMap;

import by.reshetnikov.proweather.BuildConfig;


public final class ApiQuery {

    private static final String LOCATION_NAME_PARAM = "q";
    private static final String LOCATION_ID_PARAM = "id";
    private static final String LATITUDE_PARAM = "lat";
    private static final String LONGITUDE_PARAM = "lon";
    private static final String ZIP_CODE_PARAM = "zip";
    private static final String RESULT_COUNT_PARAM = "cnt";
    private static final String SEARCH_TYPE_PARAM = "type";
    private static final String API_KEY_PARAM = "appId";

    private HashMap<String, String> query;

    public ApiQuery() {
        query = new HashMap<>();
    }

    public ApiQuery addLocationName(String locationName) {
        query.put(LOCATION_NAME_PARAM, locationName);
        return this;
    }

    public ApiQuery addLocationNameWithCountyCode(String locationName, String countyCode) {
        query.put(LOCATION_NAME_PARAM, locationName + "," + countyCode);
        return this;
    }

    public ApiQuery addLocationId(String locationId) {
        query.put(LOCATION_ID_PARAM, locationId);
        return this;
    }

    public ApiQuery addLatitudeAndLongitude(double latitude, double longitude) {
        query.put(LATITUDE_PARAM, String.valueOf(latitude));
        query.put(LONGITUDE_PARAM, String.valueOf(longitude));
        return this;
    }

    public ApiQuery addZipCode(String zipCode) {
        query.put(ZIP_CODE_PARAM, zipCode);
        return this;
    }

    public ApiQuery addCount(int count) {
        query.put(RESULT_COUNT_PARAM, String.valueOf(count));
        return this;
    }

    public ApiQuery searchTypeAccurate() {
        query.put(SEARCH_TYPE_PARAM, "accurate");
        return this;
    }

    public ApiQuery searchTypeLike() {
        query.put(SEARCH_TYPE_PARAM, "like");
        return this;
    }

    public HashMap<String, String> build() {
        query.put(API_KEY_PARAM, getApiKey());
        return query;
    }

    private String getApiKey() {
        return BuildConfig.OWM_API_KEY;
    }

}
