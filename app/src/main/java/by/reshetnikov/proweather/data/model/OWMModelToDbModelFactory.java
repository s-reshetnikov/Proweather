package by.reshetnikov.proweather.data.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.network.openweathermap.model.Wind;
import by.reshetnikov.proweather.data.network.openweathermap.model.currentweather.CurrentForecastApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.ForecastByHoursApiModel;
import by.reshetnikov.proweather.data.network.openweathermap.model.forecastweather.HourlyForecastApiModel;
import by.reshetnikov.proweather.utils.CalendarUtil;


public class OWMModelToDbModelFactory {

    // API limitation
    private static int maxDaysInForecast = 5;

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

    public static List<HoursForecastEntity> createHourlyForecastsFromAPI(HourlyForecastApiModel apiModel) {
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
            entity.setWindDegrees((int) Math.round(hourlyForecast.getWind().getDegrees()));
            entity.setWindSpeed(hourlyForecast.getWind().getSpeed());
            forecast.add(entity);
        }
        return forecast;
    }

    public static List<DailyForecastEntity> createDailyForecastsFromAPI(HourlyForecastApiModel hourlyForecastApiModel) {
        List<DailyForecastEntity> forecasts = new ArrayList<>();
        HashMap<Integer, List<ForecastByHoursApiModel>> daysForecasts = divideForecastsByDays(hourlyForecastApiModel.getForecasts());
        for (int dayNumber = 1; dayNumber <= daysForecasts.size(); dayNumber++) {
            List<ForecastByHoursApiModel> dayModels = daysForecasts.get(dayNumber);
            DailyForecastEntity dailyForecast = new DailyForecastEntity();
            dailyForecast.setDate(dayModels.get(0).getDate());
            setTemperatureToDailyForecast(dayModels, dailyForecast);
            setIconCodeToDailyForecast(dayModels, dailyForecast);
            setTemperatureForDayParts(dayModels, dailyForecast);
            dailyForecast.setPressure(getPressureForDailyForecast(dayModels));
            dailyForecast.setHumidity(getHumidityForDailyForecast(dayModels));
            setWindDetails(hourlyForecastApiModel, dailyForecast);
            setLocationToDailyForecast(hourlyForecastApiModel, dailyForecast);
            forecasts.add(dailyForecast);
        }
        return forecasts;
    }

    private static void setTemperatureForDayParts(List<ForecastByHoursApiModel> forecasts, DailyForecastEntity dailyForecast) {
        dailyForecast.setMorningTemperature(getMorningTemperature(forecasts));
        dailyForecast.setDayTemperature(getDayTemperature(forecasts));
        dailyForecast.setEveningTemperature(getEveningTemperature(forecasts));
        dailyForecast.setNightTemperature(getNightTemperature(forecasts));
    }

    private static int getMorningTemperature(List<ForecastByHoursApiModel> forecasts) {
        int nineOclockForecastIndex = 3;
        return (int) Math.round(forecasts.get(nineOclockForecastIndex).getMain().getTemperature());

    }

    private static int getDayTemperature(List<ForecastByHoursApiModel> forecasts) {

        int nineOclockForecastIndex = 5;
        return (int) Math.round(forecasts.get(nineOclockForecastIndex).getMain().getTemperature());

    }

    private static int getEveningTemperature(List<ForecastByHoursApiModel> forecasts) {
        int twentyOneOclockForecastIndex = 6;
        return (int) Math.round(forecasts.get(twentyOneOclockForecastIndex).getMain().getTemperature());

    }

    private static int getNightTemperature(List<ForecastByHoursApiModel> forecasts) {
        int threeOclockForecastIndex = 7;
        return (int) Math.round(forecasts.get(threeOclockForecastIndex).getMain().getTemperature());

    }

    private static void setLocationToDailyForecast(HourlyForecastApiModel apiModel, DailyForecastEntity dailyForecast) {
        if (apiModel.getLocation() != null)
            dailyForecast.setLocationId(apiModel.getLocation().getLocationId());
        else
            dailyForecast.setLocationId(apiModel.getCity().getId());
    }

    private static void setWindDetails(HourlyForecastApiModel apiModel, DailyForecastEntity entity) {
        Wind wind = getWind(apiModel.forecasts);
        entity.setWindDegrees((int) Math.round(wind.getDegrees()));
        entity.setWindSpeed(wind.getSpeed());
    }

    private static Wind getWind(List<ForecastByHoursApiModel> forecasts) {
        Wind wind = forecasts.get(0).getWind();
        for (ForecastByHoursApiModel apiModel : forecasts) {
            if (wind.getSpeed() < apiModel.getWind().getSpeed())
                wind = apiModel.getWind();
        }
        return wind;
    }

    private static HashMap<Integer, List<ForecastByHoursApiModel>> divideForecastsByDays(List<ForecastByHoursApiModel> forecastEntities) {
        HashMap<Integer, List<ForecastByHoursApiModel>> daysForecasts = new HashMap<>();

        Log.i("OWM factory", "divideForecastsByDays");
        for (int numOfDaysFromToday = 1; numOfDaysFromToday <= maxDaysInForecast; numOfDaysFromToday++) {
            long forecastDate = CalendarUtil.getDateFromToday(numOfDaysFromToday);
            long forecastDateDayAfter = CalendarUtil.getDateFromToday(numOfDaysFromToday + 1);
            List<ForecastByHoursApiModel> forecastsForDay = new ArrayList<>();
            for (ForecastByHoursApiModel forecastApiModel : forecastEntities) {
                if (forecastApiModel.getDate() >= forecastDate && forecastApiModel.getDate() < forecastDateDayAfter)
                    forecastsForDay.add(forecastApiModel);
            }
            Log.i("OWM", "count elements in  forecast " + forecastsForDay.size());
            if (forecastsForDay.size() == 8)
                daysForecasts.put(numOfDaysFromToday, forecastsForDay);
        }

        return daysForecasts;
    }

    private static void setTemperatureToDailyForecast(List<ForecastByHoursApiModel> forecasts, DailyForecastEntity entity) {
        if (forecasts.size() == 0)
            return;

        int min = (int) forecasts.get(0).getMain().getTemperature();
        int max = (int) forecasts.get(0).getMain().getTemperature();

        // the first element initialize min, max values that's why start with the second
        for (int index = 1; index < forecasts.size(); index++) {
            ForecastByHoursApiModel hoursForecast = forecasts.get(index);
            if (hoursForecast.getMain().getTemperature() < min)
                min = (int) hoursForecast.getMain().getTemperature();
            if (hoursForecast.getMain().getTemperature() > max)
                max = (int) hoursForecast.getMain().getTemperature();
        }
        entity.setMinTemperature(min);
        entity.setMaxTemperature(max);
    }

    private static void setIconCodeToDailyForecast(List<ForecastByHoursApiModel> hoursApiModels, DailyForecastEntity dailyForecast) {
        int thirteenHoursInSeconds = 60 * 60 * 13;
        int seventeenHoursInSeconds = 60 * 60 * 17;
        int dateForThirteenHours = CalendarUtil.getDateWithoutTime(hoursApiModels.get(0).getDate()) + thirteenHoursInSeconds;
        int dateForSeventeenHours = CalendarUtil.getDateWithoutTime(hoursApiModels.get(0).getDate()) + seventeenHoursInSeconds;

        for (ForecastByHoursApiModel apiModel : hoursApiModels) {
            if (apiModel.getDate() > dateForThirteenHours && apiModel.getDate() < dateForSeventeenHours) {
                dailyForecast.setWeatherConditionId(apiModel.getWeather().getId());
                return;
            }
        }

        int latestPosition = hoursApiModels.size() - 1;
        int iconCode = hoursApiModels.get(latestPosition).getWeather().getId();
        dailyForecast.setWeatherConditionId(iconCode);
    }

    private static int getHumidityForDailyForecast(List<ForecastByHoursApiModel> forecasts) {
        if (forecasts.size() == 0)
            return 0;

        int humidity = forecasts.get(0).getMain().getHumidity();
        // the first element initialize min, max values that's why start with the second
        for (int index = 1; index < forecasts.size(); index++) {
            int humidityForHour = forecasts.get(index).getMain().getHumidity();
            if (humidityForHour > humidity)
                humidity = humidityForHour;
        }

        return humidity;
    }

    private static int getPressureForDailyForecast(List<ForecastByHoursApiModel> forecasts) {
        if (forecasts.size() == 0)
            return 0;

        int pressure = (int) Math.round(forecasts.get(0).getMain().getPressure());

        // the first element initialize min, max values that's why start with the second
        for (int index = 1; index < forecasts.size(); index++) {
            int pressureForHour = (int) Math.round(forecasts.get(index).getMain().getPressure());
            if (pressureForHour > pressure)
                pressure = pressureForHour;
        }

        return pressure;
    }

}
