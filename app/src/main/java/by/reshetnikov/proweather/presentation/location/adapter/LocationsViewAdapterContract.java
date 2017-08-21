package by.reshetnikov.proweather.presentation.location.adapter;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.location.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.presentation.location.listener.OnLocationsOrderChangedListener;


public interface LocationsViewAdapterContract {

    void updateView(List<LocationEntity> updatedLocations);

    void setOnLocationsOrderChangedListener(OnLocationsOrderChangedListener listener);

    void setOnLocationRemovedListener(OnLocationRemovedListener listener);

    void addLocation(LocationEntity location);

    void moveLocationItem(int fromPosition, int toPosition, boolean saveChanges);

    void removeLocation(int position);

    LocationEntity getLocation(int position);
}
