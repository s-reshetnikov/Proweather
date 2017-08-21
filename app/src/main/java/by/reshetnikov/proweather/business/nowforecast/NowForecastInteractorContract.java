package by.reshetnikov.proweather.business.nowforecast;

import android.support.v4.util.Pair;

import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.presentation.nowforecast.HourlyForecastForChartViewModel;
import io.reactivex.Single;

/**
 * Created by s-reshetnikov.
 */

public interface NowForecastInteractorContract {

    Single<Pair<NowForecastViewModel, HourlyForecastForChartViewModel>> getForecastDataPair();

    Single<NowForecastViewModel> getNowForecast();

    Single<HourlyForecastForChartViewModel> getHourlyForecastForChart();
}
