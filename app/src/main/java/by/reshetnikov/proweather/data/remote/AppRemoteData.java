package by.reshetnikov.proweather.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.AppDataContract;
import by.reshetnikov.proweather.data.ForecastType;
import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.data.models.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.utils.ApiUtils;
import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by SacRahl on 6/6/2017.
 */

public class AppRemoteData implements AppDataContract {

    private static final String TAG = AppRemoteData.class.getSimpleName();

    @Inject
    Retrofit retrofit;

    public AppRemoteData() {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getCurrentWeather() {
        Log.d(TAG, "getCurrentWeather() start");

        APIService apiService = retrofit.create(APIService.class);
        Observable<CurrentWeather> currentWeather = apiService.getCurrentWeather(getApiQuery());
        Log.d(TAG, "getCurrentWeather() end");
        return null;
    }

    @NonNull
    private HashMap<String, String> getApiQuery() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(ApiUtils.CITY_NAME_PARAM, "Minsk, BY");
        queryParams.put(ApiUtils.API_KEY_PARAM, ApiUtils.getApiKey());
        return queryParams;
    }

    @Override
    public Observable<ForecastWeather> getForecastWeather(ForecastType forecastType) {
        return null;
    }
}
