package by.reshetnikov.proweather.data;

import by.reshetnikov.proweather.model.apimodels.currentweather.CurrentWeather;
import by.reshetnikov.proweather.model.apimodels.location.LocationWeather;
import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.model.dbmodels.LocationEntity;
import by.reshetnikov.proweather.model.dbmodels.WeatherEntity;


public class AppDataModelsBuilder {

    public static CurrentWeatherAppModel createCurrentWeatherModel(WeatherEntity entity) {
        CurrentWeatherAppModel model = new CurrentWeatherAppModel();
        model.setTemperature(entity.getTemperature());
        model.setHumidity(entity.getHumidity());
        model.setWindDegrees(entity.getWindDegrees());
        model.setWindSpeed(entity.getWindSpeed());
        model.setPressure(entity.getPressure());
        model.setWeatherConditionId(entity.getWeatherConditionId());
        model.setWeatherDescription(entity.getWeatherDescription());
        model.setIconCode(entity.getIconCode());
        model.setRain(entity.getRain());
        model.setSnow(entity.getSnow());
        return model;
    }

    public static CurrentWeatherAppModel createCurrentWeatherModel(CurrentWeather apiModel) {
        CurrentWeatherAppModel model = new CurrentWeatherAppModel();
        model.setTemperature(apiModel.main.temperature);
        model.setHumidity(apiModel.main.humidity);
        model.setWindDegrees(apiModel.wind.degrees);
        model.setWindSpeed(apiModel.wind.speed);
        model.setPressure(apiModel.main.pressure);
        model.setWeatherConditionId(apiModel.weather.get(0).id);
        model.setWeatherDescription(apiModel.weather.get(0).description);
        model.setIconCode(apiModel.weather.get(0).icon);
        model.setRain(apiModel.rain.rainVolume);
        model.setSnow(apiModel.snow.snowVolume);
        return model;
    }

    public static WeatherEntity createWeatherEntity(CurrentWeather apiModel) {
        WeatherEntity entity = new WeatherEntity();
        entity.setTemperature(apiModel.main.temperature);
        entity.setHumidity(apiModel.main.humidity);
        entity.setWindDegrees(apiModel.wind.degrees);
        entity.setWindSpeed(apiModel.wind.speed);
        entity.setPressure(apiModel.main.pressure);
        entity.setWeatherConditionId(apiModel.weather.get(0).id);
        entity.setWeatherDescription(apiModel.weather.get(0).description);
        entity.setIconCode(apiModel.weather.get(0).icon);
        entity.setRain(apiModel.rain.rainVolume);
        entity.setSnow(apiModel.snow.snowVolume);
        return entity;
    }

    public static LocationAppModel createLocationAppModel(LocationEntity entity) {
        LocationAppModel appModel = new LocationAppModel(entity.getLocationId(), entity.getLocationName());
        appModel.setCoordinates(entity.getLatitude(), entity.getLongitude());
        if (entity.getCountryCode() != null)
            appModel.setCountryCode(entity.getCountryCode());
        appModel.setCurrent(entity.getIsCurrent());
        return appModel;
    }

    public static LocationAppModel createLocationAppModel(LocationWeather locationWeather) {
        LocationAppModel appModel = new LocationAppModel(locationWeather.getLocationId(), locationWeather.getLocationName());
        appModel.setCoordinates(locationWeather.getCoordinates().getLatitude(), locationWeather.getCoordinates().getLongitude());
        if (locationWeather.getCountryCode() != null)
            appModel.setCountryCode(locationWeather.getCountryCode());
        appModel.setCurrent(false);
        return appModel;
    }

    public static LocationEntity createLocationEntity(LocationAppModel appModel) {
        LocationEntity entity = new LocationEntity();
        entity.setLocationId(appModel.getLocationId());
        entity.setLocationName(appModel.getLocationName());
        if (appModel.getCountryCode() != null)
            entity.setCountryCode(appModel.getCountryCode());
        entity.setCoordinates(appModel.getLatitude(), appModel.getLongitude());
        entity.setCurrent(appModel.isCurrent());
        return entity;
    }


}
