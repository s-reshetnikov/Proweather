package by.reshetnikov.proweather.data.model;

import by.reshetnikov.proweather.data.db.model.LocationEntity;

public interface LocationAdapterContract {

    LocationEntity getAdaptee();

    int getLocationId();

    void setLocationId(int locationId);

    String getLocationName();

    String setLocationName();

    String getCountryCode();

    void setCountryCode(String countryCode);

    double getLatitude();

    double getLongitude();

    void setCoordinates(double latitude, double longitude);

    boolean isCurrent();

    void setCurrent(boolean current);

    int getPosition();

    void setPosition(int position);
}
