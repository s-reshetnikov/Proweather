package by.reshetnikov.proweather.data.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.AppDataContract;
import by.reshetnikov.proweather.data.DistanceUnits;
import by.reshetnikov.proweather.data.ForecastType;
import by.reshetnikov.proweather.data.TemperatureUnits;
import by.reshetnikov.proweather.data.WindSpeedUnits;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.data.appmodels.CityAppModel;
import io.reactivex.Observable;
import io.reactivex.Single;


public class AppLocalData implements AppDataContract {

    private static final String USE_CURRENT_LOCATION_KEY = "use_current_location";
    private static final String TEMPERATURE_UNIT_KEY = "temperature_unit";
    private static final String DISTANCE_UNIT_KEY = "distance_unit";
    private static final String WIND_SPEED_UNIT_KEY = "wind_speed_unit";

    private Context context;
    private SharedPreferences preferences;

    public AppLocalData(Context context) {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    @Override
    public Observable<CurrentWeather> getCurrentWeather() {
        return null;
    }

    @Override
    public Observable<ForecastWeather> getForecastWeather(ForecastType forecastType) {
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
        boolean value = false;
        if (preferences.contains(USE_CURRENT_LOCATION_KEY))
            value = preferences.getBoolean(USE_CURRENT_LOCATION_KEY, false);
        return value;
    }

    @Override
    public TemperatureUnits getTemperatureUnit() {
        if (preferences.contains(TEMPERATURE_UNIT_KEY)) {
            int unitValue = Integer.valueOf(preferences.getString(TEMPERATURE_UNIT_KEY, "0"));
            switch (unitValue) {
                case 0:
                    return TemperatureUnits.CELSIUS;
                case 1:
                    return TemperatureUnits.KELVIN;
                case 2:
                    return TemperatureUnits.FAHRENHEIT;
            }
        }
        return null;
    }

    @Override
    public DistanceUnits getDistanceUnit() {
        if (preferences.contains(DISTANCE_UNIT_KEY)) {
            int unitValue = Integer.valueOf(preferences.getString(DISTANCE_UNIT_KEY, "0"));
            switch (unitValue) {
                case 0:
                    return DistanceUnits.METER;
                case 1:
                    return DistanceUnits.MILE;
            }
        }
        return null;
    }

    @Override
    public WindSpeedUnits getWindSpeedUnit() {
        if (preferences.contains(WIND_SPEED_UNIT_KEY)) {
            int unitValue = Integer.valueOf(preferences.getString(WIND_SPEED_UNIT_KEY, "0"));
            switch (unitValue) {
                case 0:
                    return WindSpeedUnits.METERES_PER_SECOND;
                case 1:
                    return WindSpeedUnits.MILES_PER_HOUR;
            }
        }
        return null;
    }
}
