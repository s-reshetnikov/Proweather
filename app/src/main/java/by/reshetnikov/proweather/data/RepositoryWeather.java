package by.reshetnikov.proweather.data;

import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.data.models.ForecastWeatherModels.ForecastWeather;
import by.reshetnikov.proweather.data.remote.APIService;
import io.reactivex.Observable;

/**
 * Created by SacRahl on 5/31/2017.
 */

public class RepositoryWeather implements AppDataContract {

    APIService networkService;

    public RepositoryWeather(APIService networkService) {
        this.networkService = networkService;
    }

    @Override
    public Observable<CurrentWeather> getCurrentWeather() {
        return null;
    }

    @Override
    public Observable<ForecastWeather> getForecastWeather(ForecastType forecastType) {
        return null;
    }
}
