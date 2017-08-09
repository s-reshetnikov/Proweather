package by.reshetnikov.proweather.ui.location.adapter;

import java.util.List;

import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.ui.location.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.ui.location.listener.OnLocationsOrderChangedListener;


public interface LocationsViewAdapterContract {

    void updateView(List<LocationContract> updatedLocations);

    void setOnLocationsOrderChangedListener(OnLocationsOrderChangedListener listener);

    void setOnLocationRemovedListener(OnLocationRemovedListener listener);

    void addLocation(LocationContract location);

    void moveLocationItem(int fromPosition, int toPosition, boolean saveChanges);

    void removeLocation(int position);

    LocationContract getLocation(int position);
}
