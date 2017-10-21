package by.reshetnikov.proweather.business.nowforecast;

import android.support.v4.util.Pair;

import by.reshetnikov.proweather.data.model.weather.nowforecast.HourlyChartData;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import io.reactivex.Single;

/**
 * Created by s-reshetnikov.
 */

public interface NowForecastInteractorContract {

    Single<NowForecastViewModel> getNowForecastData();

    Single<HourlyChartData> getDataForChart();

    Single<Pair<NowForecastViewModel, HourlyChartData>> getForecasts();
}
