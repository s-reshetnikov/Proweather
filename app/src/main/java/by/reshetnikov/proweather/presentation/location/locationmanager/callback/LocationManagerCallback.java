package by.reshetnikov.proweather.presentation.location.locationmanager.callback;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

/**
 * Created by s-reshetnikov.
 */

public interface LocationManagerCallback {

    void onLocationRemoved();

    void onLocationAdded(LocationEntity location);

    void onLocationsChanged();

    void onOpenMapClicked();

    void onLocationClicked(LocationEntity location);
}