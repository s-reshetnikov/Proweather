package by.reshetnikov.proweather.ui.location.adapter;

import java.util.List;

import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.ui.location.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.ui.location.listener.OnLocationsOrderChangedListener;


public interface LocationsAdapterContract {

    void updateView(List<LocationAdapterModel> updatedLocations);

    void setOnLocationsOrderChangedListener(OnLocationsOrderChangedListener listener);

    void setOnLocationRemovedListener(OnLocationRemovedListener listener);

    void addLocation(LocationAdapterModel location);

    void moveLocationItem(int fromPosition, int toPosition, boolean saveChanges);

    void removeLocation(int position);

    LocationAdapterModel getLocation(int position);
}
