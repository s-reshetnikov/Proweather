package by.reshetnikov.proweather.contract;

import java.util.List;

import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.DailyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.HourlyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import io.reactivex.Single;


public interface AppDataContract {

    boolean canUseCurrentLocation();

    LocationAppModel getChosenLocation();

    Single<List<LocationAppModel>> getSavedLocations();

    Single<List<LocationAppModel>> getAllLocationsByName(String locationName, int count);

    Single<List<LocationAppModel>> getAllLocationsByCoordinates(double latitude, double longitude, int count);

    void saveLocation(LocationAppModel location);

    void removeLocation(LocationAppModel location);

    Single<CurrentWeatherAppModel> getCurrentWeather();

    Single<CurrentWeatherAppModel> getCurrentWeather(double latitude, double longitude);

    Single<HourlyForecastWeatherAppModel> getHourlyForecastWeather();

    Single<HourlyForecastWeatherAppModel> getHourlyForecastWeather(double latitude, double longitude);

    Single<DailyForecastWeatherAppModel> getDailyForecastWeather();

    Single<DailyForecastWeatherAppModel> getDailyForecastWeather(double latitude, double longitude);

    Single<UnitsAppModel> getUnits();

    void dispose();
}
