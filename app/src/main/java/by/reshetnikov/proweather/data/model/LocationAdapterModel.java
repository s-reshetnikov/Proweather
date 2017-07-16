package by.reshetnikov.proweather.data.model;


import by.reshetnikov.proweather.data.db.model.LocationEntity;

public class LocationAdapterModel implements LocationAdapterContract {


    private LocationEntity locationEntity;

    protected LocationAdapterModel(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }

//    @Override
//    public int hashCode() {
//        return Integer.valueOf(locationEntity.getLocationId()).hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof LocationAdapterModel &&
//                ((LocationAdapterModel) obj).getLocationId() == this.getLocationId();
//    }

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
    public String setLocationName() {
        return null;
    }

    @Override
    public String getCountryCode() {
        return locationEntity.getCountryCode();
    }

    @Override
    public void setCountryCode(String countryCode) {
        this.locationEntity.setCountryCode(countryCode);
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
    public void setCoordinates(double latitude, double longitude) {
        this.locationEntity.setLatitude(latitude);
        this.locationEntity.setLatitude(longitude);
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
