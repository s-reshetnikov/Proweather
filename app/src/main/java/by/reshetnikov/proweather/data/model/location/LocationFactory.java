package by.reshetnikov.proweather.data.model.location;


import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationWeatherApiModel;

public class LocationFactory {

    public static LocationEntity create(LocationWeatherApiModel apiModel) {
        LocationEntity locationEntity = createLocationEntity(apiModel);
        return locationEntity;
    }

    private static LocationEntity createLocationEntity(LocationWeatherApiModel apiModel) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationId(apiModel.getLocationId());
        locationEntity.setLocationName(apiModel.getLocationName());
        locationEntity.setCountryCode(apiModel.getCountryCode());
        locationEntity.setLatitude(apiModel.getLatitude());
        locationEntity.setLongitude(apiModel.getLongitude());
        locationEntity.setPosition(0);
        locationEntity.setCurrent(false);
        return locationEntity;
    }
}
