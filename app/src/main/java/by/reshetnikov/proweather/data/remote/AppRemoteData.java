package by.reshetnikov.proweather.data.remote;

import android.support.annotation.NonNull;

import java.util.HashMap;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.ForecastType;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.utils.ApiUtils;
import io.reactivex.Observable;
import retrofit2.Retrofit;


public class AppRemoteData implements RemoteDataContract {

    private static final String TAG = AppRemoteData.class.getSimpleName();

    @Inject
    Retrofit retrofit;

    WeatherApi weatherApi;

    public AppRemoteData() {
        ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
        weatherApi = retrofit.create(WeatherApi.class);
    }

    @Override
    public Observable<CurrentWeather> getCurrentWeather() {
        return weatherApi.getCurrentWeather(getCurrentWeatherApiQuery());
    }

    @Override
    public Observable<ForecastWeather> getForecastWeather(ForecastType forecastType) {
        return weatherApi.getForecastWeather(getForecastWeatherApiQuery());
    }

    @NonNull
    private HashMap<String, String> getCurrentWeatherApiQuery() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(ApiUtils.CITY_NAME_PARAM, "Minsk, BY");
        queryParams.put(ApiUtils.API_KEY_PARAM, ApiUtils.getApiKey());
        return queryParams;
    }

    @NonNull
    private HashMap<String, String> getForecastWeatherApiQuery() {
        return getCurrentWeatherApiQuery();
    }
}
