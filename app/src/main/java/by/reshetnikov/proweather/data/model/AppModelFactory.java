package by.reshetnikov.proweather.data.model;


import by.reshetnikov.proweather.data.db.model.CurrentWeatherEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.network.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.model.location.LocationWeatherApiModel;

public class AppModelFactory {


    public static LocationAdapterModel create(LocationEntity entity) {
        return new LocationAdapterModel(entity);
    }

    public static LocationAdapterModel create(LocationWeatherApiModel apiModel) {
        LocationEntity locationEntity = createLocationEntity(apiModel);
        return new LocationAdapterModel(locationEntity);
    }

    private static LocationEntity createLocationEntity(LocationWeatherApiModel apiModel) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationId(apiModel.getLocationId());
        locationEntity.setLocationName(apiModel.getLocationName());
        locationEntity.setCountryCode(apiModel.getCountryCode());
        locationEntity.setLatitude(apiModel.getLatitude());
        locationEntity.setLongitude(apiModel.getLongitude());
        locationEntity.setPosition(0);
        locationEntity.setIsCurrent(false);
        return locationEntity;
    }

    public static CurrentWeatherModel create(CurrentWeatherApiModel apiModel) {
        return null;
    }

    public static CurrentWeatherModel create(CurrentWeatherEntity currentWeatherEntity) {
        return null;
    }
}
