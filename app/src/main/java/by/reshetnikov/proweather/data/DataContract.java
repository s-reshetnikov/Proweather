package by.reshetnikov.proweather.data;

import android.location.Location;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.DailyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.HourlyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import io.reactivex.Observable;


public interface DataContract {

    Observable<CurrentWeatherAppModel> getCurrentWeather(String locationId);

    Observable<HourlyForecastWeatherAppModel> getHourlyForecastWeather(String locationId);

    Observable<DailyForecastWeatherAppModel> getDailyForecastWeather(String locationId);

    Observable<LocationAppModel> getAllLocationsByName(String locationName);

    Observable<LocationEntity> getChosenLocation();

    Observable<List<LocationAppModel>> getSavedLocations();

    Observable<Boolean> saveNewLocation(LocationAppModel location);

    Observable<Boolean> updateLocation(LocationAppModel location);

    Observable<Boolean> removeLocation(LocationAppModel location);

    void setCurrentLocationPreference(Location location);

    boolean getLocationUpdateRequestedPreference();

    void setLocationUpdateRequestedPreference(boolean value);

    boolean getCanUseCurrentLocationPreference();

    UnitsAppModel getUnits();

}
