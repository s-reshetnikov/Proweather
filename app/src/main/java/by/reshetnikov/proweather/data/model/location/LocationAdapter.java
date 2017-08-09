package by.reshetnikov.proweather.data.model.location;


import by.reshetnikov.proweather.data.db.model.LocationEntity;

public class LocationAdapter implements LocationContract {


    private LocationEntity locationEntity;

    public LocationAdapter(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }

    @Override
    public int getLocationId() {
        return locationEntity.getLocationId();
    }

    @Override
    public void setLocationId(int locationId) {
        locationEntity.setLocationId(locationId);
    }

    @Override
    public String getLocationName() {
        return locationEntity.getLocationName();
    }


    @Override
    public String getCountryCode() {
        return locationEntity.getCountryCode();
    }

    @Override
    public double getLongitude() {
        return locationEntity.getLongitude();
    }

    @Override
    public double getLatitude() {
        return locationEntity.getLatitude();
    }

    @Override
    public boolean isCurrent() {
        return locationEntity.getIsCurrent();
    }

    @Override
    public void setCurrent(boolean current) {
        locationEntity.setCurrent(current);
    }

    @Override
    public int getPosition() {
        return locationEntity.getPosition();
    }

    @Override
    public void setPosition(int position) {
        locationEntity.setPosition(position);
    }

    public LocationEntity getAdaptee() {
        return locationEntity;
    }
}
