package by.reshetnikov.proweather.presentation.location.locationmanager.adapter;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.location.locationmanager.listener.OnLocationClickedListener;
import by.reshetnikov.proweather.presentation.location.locationmanager.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.presentation.location.locationmanager.listener.OnLocationsOrderChangedListener;


public interface LocationsViewAdapterContract {

    void updateView(List<LocationEntity> updatedLocations);

    void setOnLocationsOrderChangedListener(OnLocationsOrderChangedListener listener);

    void setOnLocationRemovedListener(OnLocationRemovedListener listener);

    void setOnLocationClickedListener(OnLocationClickedListener listener);

    void addLocation(LocationEntity location);

    void moveLocationItem(int fromPosition, int toPosition, boolean saveChanges);

    void removeLocation(int position);

    LocationEntity getLocation(int position);
}
