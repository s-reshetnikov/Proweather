package by.reshetnikov.proweather.contract;

import java.util.List;

import by.reshetnikov.proweather.model.appmodels.LocationAppModel;


public interface LocationsAdapterContract {
    void updateView(List<LocationAppModel> locations);

    void addLocation(LocationAppModel location);

    LocationAppModel getLocation(int position);

    void removeLocation(int position);

    void onLocationItemMove(int fromPosition, int toPosition);


}
