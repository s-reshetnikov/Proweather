package by.reshetnikov.proweather.presentation.location.map;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

/**
 * Created by s-reshetnikov.
 */

public interface MapFragmentCommunication {

    void updateLocationMarkersRequest();

    void updateLocationMarkersWithZoomRequest(LocationEntity location);

    void zoomToMarkerRequest(LocationEntity location);
}
