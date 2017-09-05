package by.reshetnikov.proweather.business.forecast;

import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.DailyForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.presentation.forecast.DailyForecastViewModel;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by s-reshetnikov.
 */

public class ForecastInteractor implements ForecastInteractorContract {
    private SchedulerProvider scheduler;
    private DataContract data;

    @Inject
    public ForecastInteractor(DataContract data, SchedulerProvider scheduler) {
        this.data = data;
        this.scheduler = scheduler;
    }

    @Override
    public Single<List<DailyForecastViewModel>> getForecasts() {
        return data.getChosenLocation()
                .flatMap(new Function<LocationEntity, SingleSource<List<DailyForecastEntity>>>() {
                    @Override
                    public SingleSource<List<DailyForecastEntity>> apply(LocationEntity locationEntity) throws Exception {
                        return data.getDailyForecasts(locationEntity);
                    }
                })
                .zipWith(data.getUnits(), new BiFunction<List<DailyForecastEntity>, Units, Pair<List<DailyForecastEntity>, Units>>() {
                    @Override
                    public Pair<List<DailyForecastEntity>, Units> apply(@NonNull List<DailyForecastEntity> entities, @NonNull Units units)
                            throws Exception {
                        Log.i("InteractorZip", "size of incoming array is " + entities.size());
                        return new Pair<>(entities, units);
                    }
                })
                .flatMap(new Function<Pair<List<DailyForecastEntity>, Units>, SingleSource<List<DailyForecastViewModel>>>() {
                    @Override
                    public SingleSource<List<DailyForecastViewModel>> apply
                            (@NonNull Pair<List<DailyForecastEntity>, Units> forecastsUnitsPair) throws Exception {
                        List<DailyForecastViewModel> dailyForecastViewModels = new ArrayList<>();
                        for (DailyForecastEntity forecastEntity : forecastsUnitsPair.first) {
                            DailyForecastViewModel viewModel = new DailyForecastViewModel(forecastEntity);
                            viewModel.applyUnits(forecastsUnitsPair.second);
                            dailyForecastViewModels.add(viewModel);
                        }
                        Log.i("InteractorFM", "size of incoming array is " + forecastsUnitsPair.first.size());
                        return Single.just(dailyForecastViewModels);
                    }
                });
    }

}
