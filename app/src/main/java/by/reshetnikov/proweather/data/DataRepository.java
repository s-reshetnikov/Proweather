package by.reshetnikov.proweather.data;

import android.location.Location;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.db.AppDbData;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.network.AppWeatherApiData;
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.DailyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.HourlyForecastWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import io.reactivex.Observable;


public class DataRepository implements DataContract {

    private static final String TAG = DataRepository.class.getSimpleName();

    @Inject
    AppDbData dbData;
    @Inject
    AppWeatherApiData apiData;
    @Inject
    AppSharedPreferencesData sharedPreferencesData;

    public DataRepository() {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeatherAppModel> getCurrentWeather(String locationId) {
        return null;
    }

    @Override
    public Observable<HourlyForecastWeatherAppModel> getHourlyForecastWeather(String locationId) {
        return null;
    }

    @Override
    public Observable<DailyForecastWeatherAppModel> getDailyForecastWeather(String locationId) {
        return null;
    }

    @Override
    public Observable<LocationAppModel> getAllLocationsByName(String locationName) {
        return null;
    }

    @Override
    public Observable<LocationEntity> getChosenLocation() {
        return null;
    }

    @Override
    public Observable<List<LocationAppModel>> getSavedLocations() {
        return null;
    }

    @Override
    public Observable<Boolean> saveNewLocation(LocationAppModel location) {
        return null;
    }

    @Override
    public Observable<Boolean> updateLocation(LocationAppModel location) {
        return null;
    }

    @Override
    public Observable<Boolean> removeLocation(LocationAppModel location) {
        return null;
    }

    @Override
    public void setCurrentLocationPreference(Location location) {

    }

    @Override
    public boolean getLocationUpdateRequestedPreference() {
        return false;
    }

    @Override
    public void setLocationUpdateRequestedPreference(boolean value) {

    }

    @Override
    public boolean getCanUseCurrentLocationPreference() {
        return false;
    }

    @Override
    public UnitsAppModel getUnits() {
        return null;
    }

//    @Override
//    public CurrentWeatherAppModel getCurrentWeather() {
//        if (NetworkUtils.isNetworkConnected(weatherApp)) {
//            CurrentWeather weather = appRemoteData.getCurrentWeather(getChosenLocation().getLocationId());
//            saveToDb(weather);
//            return ModelsConverter.createCurrentWeatherModel(weather);
//        }
//
//        LocationEntity locationEntity = appLocalData.getChosenLocation();
//        if (locationEntity == null)
//            throw new NoSuchElementException("no location set");
//
//        CurrentWeather weather = appRemoteData.getCurrentWeather(locationEntity.getLocationId());
//        saveToDb(weather);
//        return ModelsConverter.createCurrentWeatherModel(weather);
//    }

//    @Override
//    public CurrentWeatherAppModel getCurrentWeather(double latitude, double longitude) {
//        return null;
//    }
//
//    @Override
//    public HourlyForecastWeatherAppModel getHourlyForecastWeather() {
//        return null;
//    }
//
//    @Override
//    public HourlyForecastWeatherAppModel getHourlyForecastWeather(double latitude, double longitude) {
//        return null;
//    }
//
//    @Override
//    public DailyForecastWeatherAppModel getDailyForecastWeather() {
//        return null;
//    }
//
//    @Override
//    public DailyForecastWeatherAppModel getDailyForecastWeather(double latitude, double longitude) {
//        return null;
//    }
//
//    @Override
//    public boolean canUseCurrentLocation() {
//        return appLocalData.canUseCurrentLocation();
//    }
//
//    @Override
//    public LocationAppModel getChosenLocation() {
//        return ModelsConverter.createLocationAppModel(appLocalData.getChosenLocation());
//    }
//
//    @Override
//    public List<LocationAppModel> getSavedLocations() {
//        List<LocationAppModel> locations = new ArrayList<>();
//
//        for (LocationEntity entity : appLocalData.getSavedLocations()) {
//            locations.add(ModelsConverter.createLocationAppModel(entity));
//        }
//        return locations;
//    }
//
//    @Override
//    public List<LocationAppModel> getAllLocationsByName(String locationName, int count) {
//        LocationForecast locationForecast = appRemoteData.getLocations(locationName, count);
//
//        List<LocationAppModel> locationAppModels = new ArrayList<>(locationForecast.locationWeatherList.size());
//        for (LocationWeather locationWeather : locationForecast.locationWeatherList) {
//            locationAppModels.add(ModelsConverter.createLocationAppModel(locationWeather));
//        }
//
//        return locationAppModels;
//    }
//
//    @Override
//    public List<LocationAppModel> getAllLocationsByCoordinates(double latitude, double longitude, int count) {
//        return null;
//    }
//
//    @Override
//    public void saveLocation(LocationAppModel location) {
//        appLocalData.saveNewLocation(ModelsConverter.createLocationEntity(location));
//    }
//
//    @Override
//    public void saveLocations(List<LocationAppModel> locations) {
//        for (LocationAppModel appModel : locations)
//            saveLocation(appModel);
//    }
//
//    @Override
//    public void removeLocation(LocationAppModel location) {
//        appLocalData.removeLocation(ModelsConverter.createLocationEntity(location));
//    }
//
//
//    @Override
//    public UnitsAppModel getUnits() {
//        return appLocalData.getUnits();
//    }
//
//    private void saveToDb(CurrentWeather currentWeather) {
//        Log.d(TAG, "saveToDb current weather");
//        appLocalData.saveCurrentWeather(ModelsConverter.createWeatherEntity(currentWeather));
//    }
//
//    @Override
//    public void dispose() {
//        appRemoteData = null;
//        appLocalData = null;
//    }
//
//    @Override
//    public void removeLocation(int position) {
//
//    }


}
