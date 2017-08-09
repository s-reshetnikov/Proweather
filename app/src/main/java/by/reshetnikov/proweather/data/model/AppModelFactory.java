package by.reshetnikov.proweather.data.model;


import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.model.location.LocationAdapter;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapter;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentWeatherApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.location.LocationWeatherApiModel;

public class AppModelFactory {


    public static LocationAdapter create(LocationEntity entity) {
        return new LocationAdapter(entity);
    }

    public static LocationAdapter create(LocationWeatherApiModel apiModel) {
        LocationEntity locationEntity = createLocationEntity(apiModel);
        return new LocationAdapter(locationEntity);
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

    public static CurrentForecastAdapter create(CurrentWeatherApiModel apiModel) {
        CurrentForecastEntity currentWeather = new CurrentForecastEntity();
        currentWeather.setLocationId(String.valueOf(apiModel.getId()));
        currentWeather.setTemperature(apiModel.getMain().getTemperature());
        currentWeather.setRain(apiModel.getRain().getRainVolume());
        currentWeather.setSnow(apiModel.getSnow().getSnowVolume());
        currentWeather.setWindSpeed(apiModel.getWind().getSpeed());
        currentWeather.setWindDirectionDegrees((int) Math.round(apiModel.getWind().getDegrees()));
        currentWeather.setDateOfUpdate(apiModel.getDt());
        currentWeather.setHumidity(apiModel.getMain().getHumidity());
        return new CurrentForecastAdapter(currentWeather);
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

    public static CurrentForecastAdapter create(CurrentForecastEntity currentForecastEntity) {
        return null;
    }
}
