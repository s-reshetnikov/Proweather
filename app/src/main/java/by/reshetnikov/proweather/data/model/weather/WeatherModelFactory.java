package by.reshetnikov.proweather.data.model.weather;

import by.reshetnikov.proweather.data.db.model.CurrentForecastEntity;
import by.reshetnikov.proweather.data.db.model.HourlyForecastEntity;
import by.reshetnikov.proweather.data.db.model.WeatherEntity;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapter;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapterContract;
import by.reshetnikov.proweather.data.model.weather.hourly.HourlyForecastAdapter;
import by.reshetnikov.proweather.data.model.weather.hourly.HourlyForecastAdapterContract;
import by.reshetnikov.proweather.data.network.openweathermap.model.Weather;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;


public class WeatherModelFactory {

    public static CurrentForecastAdapterContract createCurrentForecastAdapter(CurrentForecastApiModel apiModel) {
        CurrentForecastEntity entity = new CurrentForecastEntity();
        entity.setLocationId(String.valueOf(apiModel.getId()));
        entity.setTemperature(apiModel.getMain().getTemperature());
        entity.setRain(apiModel.getRain().getRainVolume());
        entity.setSnow(apiModel.getSnow().getSnowVolume());
        entity.setWindSpeed(apiModel.getWind().getSpeed());
        entity.setWindDirectionDegrees((int) Math.round(apiModel.getWind().getDegrees()));
        entity.setDateOfUpdate(apiModel.getDt());
        entity.setHumidity(apiModel.getMain().getHumidity());
        WeatherEntity weatherEntity = createWeatherEntity(apiModel.getWeather());
        entity.setWeatherEntity(weatherEntity);
        return new CurrentForecastAdapter(entity);
    }

    public static CurrentForecastAdapterContract createCurrentForecastAdapter(CurrentForecastEntity entity) {
        return new CurrentForecastAdapter(entity);
    }

    public static HourlyForecastAdapterContract createHourlyForecastAdapter(HourlyForecastApiModel apiModel) {
        HourlyForecastEntity entity = new HourlyForecastEntity();
        return new HourlyForecastAdapter(entity);
    }

    public static HourlyForecastAdapterContract createHourlyForecastAdapter(HourlyForecastEntity entity) {
        return new HourlyForecastAdapter(entity);
    }

    private static WeatherEntity createWeatherEntity(Weather weather) {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setWeatherConditionId(weather.getId());
        weatherEntity.setWeatherDescription(weather.getDescription());
        return weatherEntity;
    }
}
