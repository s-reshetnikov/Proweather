package by.reshetnikov.proweather.data.preferences;

import android.location.Location;

import by.reshetnikov.proweather.data.model.unit.UnitsContract;


public interface PreferencesContract {

    Location getCurrentLocationPreference();

    void setCurrentLocationPreference(Location location);

    boolean getLocationUpdateRequestedPreference();

    void setLocationUpdateRequestedPreference(boolean value);

    boolean getCanUseCurrentLocationPreference();

    UnitsContract getUnits();
}
