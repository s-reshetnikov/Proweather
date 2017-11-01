package by.reshetnikov.proweather.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.exception.NoLocationException;
import by.reshetnikov.proweather.data.model.Coordinates;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.preferences.units.DistanceUnits;
import by.reshetnikov.proweather.data.preferences.units.SpeedUnit;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;
import by.reshetnikov.proweather.di.qualifier.ApplicationContext;
import by.reshetnikov.proweather.utils.CalendarUtil;
import io.reactivex.Completable;
import io.reactivex.Single;
import timber.log.Timber;


public class AppSharedPreferencesData implements PreferencesContract {
    private static final String DATE_PATTERN = "MM/dd/yyyy HH:mm";
    private static final String USE_CURRENT_LOCATION_KEY = "use_current_location";
    private static final String DATE_OF_LAST_LOCATION_UPDATE_KEY = "is_location_updated";
    private static final String LAST_LOCATION_KEY = "last_location";
    private static final String TEMPERATURE_UNIT_KEY = "temperature_unit";
    private static final String DISTANCE_UNIT_KEY = "distance_unit";
    private static final String WIND_SPEED_UNIT_KEY = "wind_speed_unit";
    private SharedPreferences preferences;

    @Inject
    public AppSharedPreferencesData(@ApplicationContext Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
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

    @Override
    public boolean canUseLatestLocation() {
        int locationDate = preferences.getInt(DATE_OF_LAST_LOCATION_UPDATE_KEY, 0);
        int todayDate = CalendarUtil.getTodayDateInSeconds();
        int threeHoursInSeconds = 3 * 60 * 60;
        // if update older than 3 hours ago  - last location expired
        return todayDate - locationDate < threeHoursInSeconds;
    }

    @Override
    public Completable saveLastCoordinates(Coordinates coordinates) {
        Timber.d("saveLastLocations() called");

        preferences.edit()
                .putString(LAST_LOCATION_KEY, new Gson().toJson(coordinates))
                .putInt(DATE_OF_LAST_LOCATION_UPDATE_KEY, CalendarUtil.getTodayDateInSeconds())
                .commit();
        return Completable.complete();
    }

    @Override
    public Single<Coordinates> getLastSavedCoordinates() {
        String lastLocation = preferences.getString(LAST_LOCATION_KEY, null);

        Coordinates coordinates = null;
        try {
            coordinates = new Gson().fromJson(lastLocation, Coordinates.class);
        } catch (JsonSyntaxException e) {
            return Single.error(e);
        }
        if (coordinates == null)
            return Single.error(new NoLocationException());

        return Single.just(coordinates);
    }
}
