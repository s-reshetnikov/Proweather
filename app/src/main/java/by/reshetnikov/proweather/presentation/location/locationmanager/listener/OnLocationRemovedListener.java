package by.reshetnikov.proweather.presentation.location.locationmanager.listener;


import by.reshetnikov.proweather.data.db.model.LocationEntity;

public interface OnLocationRemovedListener {

    void onRemove(LocationEntity location);
}
