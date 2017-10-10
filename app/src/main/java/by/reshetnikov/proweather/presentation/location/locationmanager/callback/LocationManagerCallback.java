package by.reshetnikov.proweather.presentation.location.locationmanager.callback;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

/**
 * Created by s-reshetnikov.
 */

public interface LocationManagerCallback {

    void onLocationRemoved();

    void onLocationAdded(LocationEntity locationEntity);

    void onLocationsChanged();

    void onOpenMapClicked();
}