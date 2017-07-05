package by.reshetnikov.proweather.contract;

import by.reshetnikov.proweather.model.appmodels.LocationAppModel;


public interface LocationsAdapterContract {

    void addLocation(LocationAppModel location);

    LocationAppModel getLocation(int position);

    void removeLocation(int position);

    void onLocationItemMove(int fromPosition, int toPosition);
}
