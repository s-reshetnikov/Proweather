package by.reshetnikov.proweather.presentation.location.locationmanager.listener;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

/**
 * Created by s-reshetnikov.
 */

public interface OnLocationClickedListener {

    void onLocationItemClicked(LocationEntity locationEntity);
}
