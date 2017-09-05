package by.reshetnikov.proweather.data.preferences;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.preferences.units.DistanceUnits;
import by.reshetnikov.proweather.data.preferences.units.SpeedUnit;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;
import by.reshetnikov.proweather.di.ApplicationContext;


public class AppSharedPreferencesData implements PreferencesContract {

    private static final String USE_CURRENT_LOCATION_KEY = "use_current_location";
    private static final String TEMPERATURE_UNIT_KEY = "temperature_unit";
    private static final String DISTANCE_UNIT_KEY = "distance_unit";
    private static final String WIND_SPEED_UNIT_KEY = "wind_speed_unit";
    private static final String LOCATION_UPDATES_REQUESTED_KEY = "location-updates-requested";
    private static final String CURRENT_LOCATION_KEY = "current-location";

    private final android.content.SharedPreferences preferences;

    @Inject
    public AppSharedPreferencesData(@ApplicationContext Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Location getCurrentLocationPreference() {
        String json = preferences.getString(CURRENT_LOCATION_KEY, null);
        return json == null ? null : new Gson().fromJson(json, Location.class);
    }

    @Override
    public void setCurrentLocationPreference(Location location) {
        String json = location == null ? null : new Gson().toJson(location);
        preferences.edit().putString(CURRENT_LOCATION_KEY, json).apply();
    }

    @Override
    public boolean getLocationUpdateRequestedPreference() {
        return preferences.getBoolean(LOCATION_UPDATES_REQUESTED_KEY, false);
    }

    @Override
    public void setLocationUpdateRequestedPreference(boolean value) {
        preferences.edit().putBoolean(LOCATION_UPDATES_REQUESTED_KEY, value).apply();
    }

    @Override
    public boolean getCanUseCurrentLocationPreference() {
        return preferences.getBoolean(USE_CURRENT_LOCATION_KEY, false);
    }

    private TemperatureUnit getTemperatureUnit() {
        int unitValue = Integer.valueOf(preferences.getString(TEMPERATURE_UNIT_KEY, "0"));
        switch (unitValue) {
            case 0:
                return TemperatureUnit.CELSIUS;
            case 1:
                return TemperatureUnit.KELVIN;
            case 2:
                return TemperatureUnit.FAHRENHEIT;
        }
        return TemperatureUnit.CELSIUS;
    }

    private DistanceUnits getDistanceUnitPreference() {
        int unitValue = Integer.valueOf(preferences.getString(DISTANCE_UNIT_KEY, "0"));
        switch (unitValue) {
            case 0:
                return DistanceUnits.METER;
            case 1:
                return DistanceUnits.MILE;
        }
        return DistanceUnits.METER;
    }

    private SpeedUnit getWindSpeedUnitPreference() {
        int unitValue = Integer.valueOf(preferences.getString(WIND_SPEED_UNIT_KEY, "0"));
        switch (unitValue) {
            case 0:
                return SpeedUnit.METRES_PER_SECOND;
            case 1:
                return SpeedUnit.MILES_PER_HOUR;
        }
        return SpeedUnit.METRES_PER_SECOND;
    }

    @Override
    public Units getUnits() {
        TemperatureUnit temperature = getTemperatureUnit();
        SpeedUnit windSpeed = getWindSpeedUnitPreference();
        DistanceUnits distance = getDistanceUnitPreference();
        return new Units(temperature, distance, windSpeed);
    }

}
