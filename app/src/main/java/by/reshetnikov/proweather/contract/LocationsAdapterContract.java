package by.reshetnikov.proweather.contract;

import java.util.List;

import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.listener.OnLocationsOrderChangedListener;


public interface LocationsAdapterContract {

    void updateView(List<LocationAdapterModel> updatedLocations);

    void setOnLocationsOrderChangedListener(OnLocationsOrderChangedListener listener);

    void setOnLocationRemovedListener(OnLocationRemovedListener listener);

    void addLocation(LocationAdapterModel location);

    void moveLocationItem(int fromPosition, int toPosition, boolean saveChanges);

    void removeLocation(int position);

    LocationAdapterModel getLocation(int position);
}
