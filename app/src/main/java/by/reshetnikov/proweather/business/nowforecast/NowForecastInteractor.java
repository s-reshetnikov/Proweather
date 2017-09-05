package by.reshetnikov.proweather.business.nowforecast;

import android.support.v4.util.Pair;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.presentation.nowforecast.HourlyForecastForChartViewModel;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by s-reshetnikov.
 */

public class NowForecastInteractor implements NowForecastInteractorContract {

    private DataContract dataManager;
    private SchedulerProvider scheduler;

    @Inject
    public NowForecastInteractor(DataContract dataManager, SchedulerProvider scheduler) {
        this.dataManager = dataManager;
        this.scheduler = scheduler;
    }

    @Override
    public Single<Pair<NowForecastViewModel, HourlyForecastForChartViewModel>> getForecastDataPair() {
        return dataManager.getChosenLocation()
                .zipWith(dataManager.getUnits(), new BiFunction<LocationEntity, Units, Pair<LocationEntity, Units>>() {
                    @Override
                    public Pair<LocationEntity, Units> apply(@NonNull LocationEntity locationEntity, @NonNull Units units) throws Exception {
                        return new Pair<>(locationEntity, units);
                    }
                })
                .flatMap(new Function<Pair<LocationEntity, Units>, SingleSource<Pair<NowForecastViewModel, Pair<LocationEntity, Units>>>>() {
                    @Override
                    public SingleSource<Pair<NowForecastViewModel, Pair<LocationEntity, Units>>> apply(@NonNull Pair<LocationEntity, Units> pair) throws Exception {
                        return dataManager.getNowForecast(pair.first)
                                .zipWith(Single.just(pair), new BiFunction<NowForecastEntity, Pair<LocationEntity, Units>, Pair<NowForecastViewModel, Pair<LocationEntity, Units>>>() {
                                    @Override
                                    public Pair<NowForecastViewModel, Pair<LocationEntity, Units>> apply(@NonNull NowForecastEntity nowForecastEntity,
                                                                                                         @NonNull Pair<LocationEntity, Units> pair) throws Exception {
                                        NowForecastViewModel nowForecast = new NowForecastViewModel(nowForecastEntity, pair.second, pair.first);
                                        return new Pair<>(nowForecast, pair);
                                    }
                                });
                    }
                })
                .flatMap(new Function<Pair<NowForecastViewModel, Pair<LocationEntity, Units>>,
                        SingleSource<? extends Pair<NowForecastViewModel, HourlyForecastForChartViewModel>>>() {
                    @Override
                    public SingleSource<? extends Pair<NowForecastViewModel, HourlyForecastForChartViewModel>> apply(
                            @NonNull final Pair<NowForecastViewModel, Pair<LocationEntity, Units>> nowForecastViewModelPairPair) throws Exception {
                        return dataManager.getHourForecasts(nowForecastViewModelPairPair.second.first)
                                .zipWith(Single.just(new Pair<>(nowForecastViewModelPairPair.first,
                                                nowForecastViewModelPairPair.second.second)),
                                        new BiFunction<List<HoursForecastEntity>,
                                                Pair<NowForecastViewModel, Units>,
                                                Pair<NowForecastViewModel, HourlyForecastForChartViewModel>>() {
                                            @Override
                                            public Pair<NowForecastViewModel, HourlyForecastForChartViewModel> apply(
                                                    @NonNull List<HoursForecastEntity> entities,
                                                    @NonNull Pair<NowForecastViewModel, Units> nowForecastViewModelUnitsPair) throws Exception {
                                                HourlyForecastForChartViewModel hourlyForecast = new HourlyForecastForChartViewModel(entities);
                                                hourlyForecast.applyUnits(nowForecastViewModelUnitsPair.second);
                                                return new Pair<>(nowForecastViewModelPairPair.first, hourlyForecast);
                                            }
                                        });
                    }
                });
    }


    @Override
    public Single<NowForecastViewModel> getNowForecast() {
        return dataManager.getChosenLocation()
                .zipWith(dataManager.getUnits(), new BiFunction<LocationEntity, Units, Pair<LocationEntity, Units>>() {
                    @Override
                    public Pair<LocationEntity, Units> apply(@NonNull LocationEntity locationEntity, @NonNull Units units) throws Exception {
                        return new Pair<>(locationEntity, units);
                    }
                })
                .flatMap(new Function<Pair<LocationEntity, Units>, SingleSource<NowForecastViewModel>>() {
                    @Override
                    public SingleSource<NowForecastViewModel> apply(@NonNull Pair<LocationEntity, Units> pair) throws Exception {
                        return dataManager.getNowForecast(pair.first)
                                .zipWith(Single.just(pair), new BiFunction<NowForecastEntity, Pair<LocationEntity, Units>, NowForecastViewModel>() {
                                    @Override
                                    public NowForecastViewModel apply(@NonNull NowForecastEntity nowForecastEntity,
                                                                      @NonNull Pair<LocationEntity, Units> pair) throws Exception {
                                        return new NowForecastViewModel(nowForecastEntity, pair.second, pair.first);
                                    }
                                });
                    }
                });
    }

    @Override
    public Single<HourlyForecastForChartViewModel> getHourlyForecastForChart() {
        return dataManager.getChosenLocation()
                .zipWith(dataManager.getUnits(), new BiFunction<LocationEntity, Units, Pair<LocationEntity, Units>>() {
                    @Override
                    public Pair<LocationEntity, Units> apply(@NonNull LocationEntity locationEntity, @NonNull Units units) throws Exception {
                        return new Pair<>(locationEntity, units);
                    }
                })
                .flatMap(new Function<Pair<LocationEntity, Units>, SingleSource<HourlyForecastForChartViewModel>>() {
                    @Override
                    public SingleSource<HourlyForecastForChartViewModel> apply(@NonNull Pair<LocationEntity, Units> locationUnitsPair) throws Exception {
                        return dataManager.getHourForecasts(locationUnitsPair.first)
                                .zipWith(Single.just(locationUnitsPair.second),
                                        new BiFunction<List<HoursForecastEntity>, Units, HourlyForecastForChartViewModel>() {
                                            @Override
                                            public HourlyForecastForChartViewModel apply(@NonNull List<HoursForecastEntity> entities,
                                                                                         @NonNull Units units) throws Exception {
                                                HourlyForecastForChartViewModel hourlyChartForecasts = new HourlyForecastForChartViewModel(entities);
                                                hourlyChartForecasts.applyUnits(units);
                                                return hourlyChartForecasts;
                                            }
                                        });
                    }
                });
    }
}
