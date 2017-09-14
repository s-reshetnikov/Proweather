package by.reshetnikov.proweather.business.forecast;

import java.util.List;

import by.reshetnikov.proweather.presentation.dailyforecast.DailyForecastViewModel;
import io.reactivex.Single;

/**
 * Created by s-reshetnikov.
 */

public interface ForecastInteractorContract {
    Single<List<DailyForecastViewModel>> getForecasts();
}
