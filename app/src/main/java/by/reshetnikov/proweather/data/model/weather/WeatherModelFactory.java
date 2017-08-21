package by.reshetnikov.proweather.data.model.weather;

import java.util.ArrayList;
import java.util.List;

import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.ForecastByHoursApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;


public class WeatherModelFactory {

    public static NowForecastEntity createNowForecastFromAPI(CurrentForecastApiModel apiModel) {
        NowForecastEntity entity = new NowForecastEntity();
        entity.setLocationId(String.valueOf(apiModel.getId()));
        entity.setTemperature((int) Math.round(apiModel.getMain().getTemperature()));
        if (apiModel.getRain() != null)
            entity.setRain((int) Math.round(apiModel.getRain().getRainVolume()));
        else
            entity.setRain(0);
        if (apiModel.getSnow() != null)
            entity.setSnow((int) Math.round(apiModel.getSnow().getSnowVolume()));
        else
            entity.setSnow(0);

        entity.setWindSpeed(apiModel.getWind().getSpeed());
        entity.setWindDirectionDegrees((int) Math.round(apiModel.getWind().getDegrees()));
        entity.setDateOfUpdate(apiModel.getDate());
        entity.setHumidity(apiModel.getMain().getHumidity());
        entity.setWeatherConditionId(apiModel.getWeather().getId());
        entity.setWeatherDescription(apiModel.getWeather().getDescription());
        return entity;
    }

    public static List<HoursForecastEntity> createForecastsFromAPI(HourlyForecastApiModel apiModel) {
        List<HoursForecastEntity> forecast = new ArrayList<>();
        for (ForecastByHoursApiModel hourlyForecast : apiModel.forecasts) {
            HoursForecastEntity entity = new HoursForecastEntity();
            entity.setHumidity(hourlyForecast.getMain().getHumidity());
            entity.setDate(hourlyForecast.getDate());
            if (hourlyForecast.getRain() != null)
                entity.setRain((int) Math.round(hourlyForecast.getRain().getRainVolume()));
            else
                entity.setRain(0);
            if (hourlyForecast.getSnow() != null)
                entity.setSnow((int) Math.round(hourlyForecast.getSnow().getSnowVolume()));
            else
                entity.setSnow(0);
            if (apiModel.getLocation() != null)
                entity.setLocationId(apiModel.getLocation().getLocationId());
            else
                entity.setLocationId(apiModel.getCity().getId());
            entity.setPressure((int) Math.round(hourlyForecast.getMain().getPressure()));
            entity.setTemperature((int) Math.round(hourlyForecast.getMain().getTemperature()));
            entity.setWindDegrees(hourlyForecast.getWind().getDegrees());
            entity.setWindSpeed(hourlyForecast.getWind().getSpeed());
            forecast.add(entity);
        }
        return forecast;
    }

}
