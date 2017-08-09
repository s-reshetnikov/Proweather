package by.reshetnikov.proweather.data.model.location;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

public interface LocationContract {

    LocationEntity getAdaptee();

    int getLocationId();

    void setLocationId(int locationId);

    String getLocationName();

    String getCountryCode();

    double getLatitude();

    double getLongitude();

    boolean isCurrent();

    void setCurrent(boolean current);

    int getPosition();

    void setPosition(int position);
}
