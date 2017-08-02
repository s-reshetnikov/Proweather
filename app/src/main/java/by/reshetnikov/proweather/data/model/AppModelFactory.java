package by.reshetnikov.proweather.data.model;


import java.util.ArrayList;
import java.util.List;

import by.reshetnikov.proweather.data.db.model.CurrentWeatherEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.WeatherEntity;
import by.reshetnikov.proweather.data.network.model.Weather;
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

    public static CurrentWeatherAdapterModel create(CurrentWeatherApiModel apiModel) {
        CurrentWeatherEntity currentWeather = new CurrentWeatherEntity();
        currentWeather.setLocationId(String.valueOf(apiModel.getId()));
        currentWeather.setTemperature(apiModel.getMain().getTemperature());
        currentWeather.setRain(apiModel.getRain().getRainVolume());
        currentWeather.setSnow(apiModel.getSnow().getSnowVolume());
        currentWeather.setClouds(apiModel.getClouds().getCloudiness());
        currentWeather.setPressure(apiModel.getMain().getPressure());
        currentWeather.setWindSpeed(apiModel.getWind().getSpeed());
        currentWeather.setWindDirectionDegrees((int) Math.round(apiModel.getWind().getDegrees()));
        currentWeather.setDateOfUpdate(apiModel.getDt());
        currentWeather.setHumidity(apiModel.getMain().getHumidity());
        currentWeather.setSunrise(apiModel.getSys().getSunrise());
        currentWeather.setSunset(apiModel.getSys().getSunset());
        return new CurrentWeatherAdapterModel(currentWeather);
    }

//    private static WeatherEntity create(Weather weather) {
//        WeatherEntity weatherEntity = new WeatherEntity();
//        weatherEntity.setIconCode(weather.getIcon());
//        weatherEntity.setPressure(weather.get);
//        weatherEntity.setSnow();
//        weatherEntity.setRain();
//        weatherEntity.setWeatherDescription();
//        weatherEntity.setTemperature();
//        weatherEntity.setWindSpeed();
//        weatherEntity.setWindDegrees();
//        weatherEntity.setDate();
//        weatherEntity.setHumidity();
//        weatherEntity.setLocationId();
//        weatherEntity.setWeatherConditionId();
//    }

    public static CurrentWeatherAdapterModel create(CurrentWeatherEntity currentWeatherEntity) {
        return null;
    }
}
