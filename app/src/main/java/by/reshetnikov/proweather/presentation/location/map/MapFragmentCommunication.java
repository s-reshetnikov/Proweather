package by.reshetnikov.proweather.presentation.location.map;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

/**
 * Created by s-reshetnikov.
 */

public interface MapFragmentCommunication {

    void updateLocationMarkers();

    void updateLocationMarkersWithZoom(LocationEntity locationEntity);
}
