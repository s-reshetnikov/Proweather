package by.reshetnikov.proweather.data.local;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.ForecastType;
import by.reshetnikov.proweather.data.appmodels.CityAppModel;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.ForecastWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.LocationAppModel;
import by.reshetnikov.proweather.data.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.data.local.preferences.SharedPreferencesStorage;
import io.reactivex.Observable;
import io.reactivex.Single;


public class AppLocalData implements LocalDataContract {


    private Context context;
    private SharedPreferencesStorage preferences;
    private FusedLocationProviderClient locationClient;

    public AppLocalData(Context context) {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
        this.context = context;
        preferences = SharedPreferencesStorage.getInstance(context);
        locationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public Observable<CurrentWeatherAppModel> getCurrentWeather() {
        return null;
    }

    @Override
    public Observable<ForecastWeatherAppModel> getForecastWeather(ForecastType forecastType) {
        return null;
    }

    @Override
    public Single<List<CityAppModel>> getSavedCities() {
        return null;
    }

    @Override
    public void saveCity(CityAppModel city) {

    }

    @Override
    public boolean canUseCurrentLocation() {
        return preferences.getCanUseCurrentLocationPreference();
    }

    @Override
    public LocationAppModel getCurrentLocation() {
        return null;
    }

    @Override
    public Observable<UnitsAppModel> getUnits() {
        return preferences.getChosenUnits();
    }

}
