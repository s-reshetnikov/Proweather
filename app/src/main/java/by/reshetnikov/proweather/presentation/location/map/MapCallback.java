package by.reshetnikov.proweather.presentation.location.map;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

/**
 * Created by s-reshetnikov.
 */

public interface MapCallback {

    void newLocationMarkerAdded(LocationEntity location);

    void onMarkerClicked(LocationEntity location);
}
