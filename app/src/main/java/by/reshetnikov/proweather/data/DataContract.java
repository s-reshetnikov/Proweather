package by.reshetnikov.proweather.data;

import java.util.List;

import by.reshetnikov.proweather.data.model.CurrentWeatherModel;
import by.reshetnikov.proweather.data.model.DailyForecastWeatherModel;
import by.reshetnikov.proweather.data.model.HourlyForecastWeatherModel;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.data.model.UnitsAppModel;
import io.reactivex.Observable;


public interface DataContract {

    Observable<CurrentWeatherModel> getCurrentWeather(String locationId);

    Observable<HourlyForecastWeatherModel> getHourlyForecastWeather(String locationId);

    Observable<DailyForecastWeatherModel> getDailyForecastWeather(String locationId);

    Observable<List<LocationAdapterModel>> getAllLocationsByName(String locationName, int resultsCount);

    Observable<LocationAdapterModel> getChosenLocation();

    Observable<List<LocationAdapterModel>> getSavedLocations();

    Observable<Boolean> saveNewLocation(LocationAdapterModel location);

    Observable<Boolean> removeLocation(LocationAdapterModel location);

    boolean getCanUseCurrentLocationPreference();

    UnitsAppModel getUnits();

    Observable<Boolean> updateLocationPosition(int fromPosition, int toPosition);
}
