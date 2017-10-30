package by.reshetnikov.proweather.business.nowforecastservice;

import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import io.reactivex.Single;

/**
 * Created by s-reshetnikov.
 */

public interface ServiceForecastInteractorContract {

    Single<NowForecastViewModel> getNowForecastData();
}
