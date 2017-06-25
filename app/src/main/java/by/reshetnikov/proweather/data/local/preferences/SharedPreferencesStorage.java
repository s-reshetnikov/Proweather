package by.reshetnikov.proweather.data.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import by.reshetnikov.proweather.data.DistanceUnits;
import by.reshetnikov.proweather.data.TemperatureUnit;
import by.reshetnikov.proweather.data.WindSpeedUnit;
import by.reshetnikov.proweather.data.appmodels.UnitsAppModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;


public class SharedPreferencesStorage implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = SharedPreferencesStorage.class.getSimpleName();

    private static final String USE_CURRENT_LOCATION_KEY = "use_current_location";
    private static final String TEMPERATURE_UNIT_KEY = "temperature_unit";
    private static final String DISTANCE_UNIT_KEY = "distance_unit";
    private static final String WIND_SPEED_UNIT_KEY = "wind_speed_unit";
    private static final String LOCATION_UPDATES_REQUESTED_KEY = "location-updates-requested";
    private static final String CURRENT_LOCATION_KEY = "current-location";
    private static SharedPreferencesStorage storage;
    private static SharedPreferences preferences;
    private static Context appContext;

    private PublishSubject<UnitsAppModel> unitsStream = PublishSubject.create();

    private SharedPreferencesStorage() {
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Nullable
    public static SharedPreferencesStorage getInstance() {
        if (preferences == null)
            return null;
        if (storage == null)
            storage = new SharedPreferencesStorage();
        return storage;
    }

    public static SharedPreferencesStorage getInstance(Context context) {
        if (preferences == null && context != null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            appContext = context;
        }
        return getInstance();
    }


    private Location getCurrentLocationPreference() {
        String json = preferences.getString(CURRENT_LOCATION_KEY, null);
        return json == null ? null : new Gson().fromJson(json, Location.class);
    }

    public void setCurrentLocationPreference(Location location) {
        String json = location == null ? null : new Gson().toJson(location);
        preferences.edit().putString(CURRENT_LOCATION_KEY, json).apply();
    }

    public boolean getLocationUpdateRequestedPreference() {
        return preferences.getBoolean(LOCATION_UPDATES_REQUESTED_KEY, false);
    }

    public void setLocationUpdateRequestedPreference(boolean value) {
        preferences.edit().putBoolean(LOCATION_UPDATES_REQUESTED_KEY, value).apply();
    }

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

    private WindSpeedUnit getWindSpeedUnitPreference() {
        int unitValue = Integer.valueOf(preferences.getString(WIND_SPEED_UNIT_KEY, "0"));
        switch (unitValue) {
            case 0:
                return WindSpeedUnit.METERES_PER_SECOND;
            case 1:
                return WindSpeedUnit.MILES_PER_HOUR;
        }
        return WindSpeedUnit.METERES_PER_SECOND;
    }

    private UnitsAppModel getUnits() {
        TemperatureUnit temperature = getTemperatureUnit();
        WindSpeedUnit windSpeed = getWindSpeedUnitPreference();
        DistanceUnits distance = getDistanceUnitPreference();
        return new UnitsAppModel(temperature, distance, windSpeed);
    }

    public Observable<UnitsAppModel> getChosenUnits() {
        return Observable.create(new ObservableOnSubscribe<UnitsAppModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<UnitsAppModel> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(getUnits());
                    e.onComplete();
                }
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key == TEMPERATURE_UNIT_KEY || key == DISTANCE_UNIT_KEY || key == WIND_SPEED_UNIT_KEY) {
            unitsStream.onNext(getUnits());
        }
    }
}
