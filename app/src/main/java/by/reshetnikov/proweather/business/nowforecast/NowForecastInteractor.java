package by.reshetnikov.proweather.business.nowforecast;

import android.support.v4.util.Pair;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.exception.NoLocationException;
import by.reshetnikov.proweather.data.exception.NoSavedForecastDataException;
import by.reshetnikov.proweather.data.model.Coordinates;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.model.weather.nowforecast.HourlyChartData;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.presentation.nowforecast.HourlyForecastForChartViewModel;
import by.reshetnikov.proweather.utils.UnitUtils;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;
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
    private ThreadSchedulerProvider scheduler;

    @Inject
    public NowForecastInteractor(DataContract dataManager, ThreadSchedulerProvider scheduler) {
        this.dataManager = dataManager;
        this.scheduler = scheduler;
    }

    @Override
    public Single<NowForecastViewModel> getNowForecastData() {
        return dataManager.getChosenLocation()
                .subscribeOn(scheduler.io())
                .zipWith(dataManager.getUnits(), new BiFunction<LocationEntity, Units, Pair<LocationEntity, Units>>() {
                    @Override
                    public Pair<LocationEntity, Units> apply(@NonNull LocationEntity locationEntity, @NonNull Units units) throws Exception {
                        return new Pair<>(locationEntity, units);
                    }
                })
                .subscribeOn(scheduler.io())
                .flatMap(new Function<Pair<LocationEntity, Units>, SingleSource<NowForecastViewModel>>() {
                    @Override
                    public SingleSource<NowForecastViewModel> apply(@NonNull Pair<LocationEntity, Units> pair) throws Exception {
                        return dataManager.getNowForecast(pair.first)
                                .zipWith(Single.just(pair), new BiFunction<NowForecastEntity, Pair<LocationEntity, Units>, NowForecastViewModel>() {
                                    @Override
                                    public NowForecastViewModel apply(NowForecastEntity nowForecastEntity, Pair<LocationEntity, Units> locationEntityUnitsPair) throws Exception {
                                        return new NowForecastViewModel(nowForecastEntity, pair.second, pair.first);
                                    }
                                });
                    }
                });
    }

    @Override
    public Single<HourlyChartData> getDataForChart() {
        return dataManager.getChosenLocation()
                .subscribeOn(scheduler.io())
                .zipWith(dataManager.getUnits(), new BiFunction<LocationEntity, Units, Pair<LocationEntity, Units>>() {
                    @Override
                    public Pair<LocationEntity, Units> apply(@NonNull LocationEntity locationEntity, @NonNull Units units) throws Exception {
                        return new Pair<>(locationEntity, units);
                    }
                })
                .subscribeOn(scheduler.io())
                .flatMap(new Function<Pair<LocationEntity, Units>, SingleSource<HourlyChartData>>() {
                    @Override
                    public SingleSource<HourlyChartData> apply(Pair<LocationEntity, Units> locationEntityUnitsPair) throws Exception {
                        return dataManager.getHourForecasts(locationEntityUnitsPair.first)
                                .zipWith(Single.just(locationEntityUnitsPair.second), new BiFunction<List<HoursForecastEntity>, Units, HourlyChartData>() {
                                    @Override
                                    public HourlyChartData apply(List<HoursForecastEntity> hourlyForecast, Units units) throws Exception {
                                        if (hourlyForecast.size() == 0)
                                            throw new NoSavedForecastDataException();

                                        HourlyForecastForChartViewModel hourlyChartForecasts = new HourlyForecastForChartViewModel(hourlyForecast);
                                        hourlyChartForecasts.applyUnits(units);
                                        return getDataForChart(hourlyChartForecasts);
                                    }
                                });
                    }
                });
    }

    @Override
    public Single<Pair<NowForecastViewModel, HourlyChartData>> getForecasts() {
        return getNowForecastData()
                .subscribeOn(scheduler.io())
                .zipWith(getDataForChart(), new BiFunction<NowForecastViewModel, HourlyChartData, Pair<NowForecastViewModel, HourlyChartData>>() {
                    @Override
                    public Pair<NowForecastViewModel, HourlyChartData> apply(NowForecastViewModel nowForecastViewModel, HourlyChartData chartData) throws Exception {
                        return new Pair<>(nowForecastViewModel, chartData);
                    }
                });
    }

    private Single<LocationEntity> getLocation() {
        if (dataManager.canUseCurrentLocation())
            return dataManager.getLastSavedLocation()
                    .flatMap(new Function<Coordinates, SingleSource<LocationEntity>>() {
                        @Override
                        public SingleSource<LocationEntity> apply(Coordinates coordinates) throws Exception {
                            int resultsCount = 1;
                            return dataManager.getLocationsByCoordinates(coordinates.getLatitude(), coordinates.getLongitude(), 1)
                                    .flatMap(new Function<List<LocationEntity>, SingleSource<LocationEntity>>() {
                                        @Override
                                        public SingleSource<LocationEntity> apply(List<LocationEntity> locationEntities) {
                                            if (locationEntities.size() == 0)
                                                return Single.error(new NoLocationException());
                                            int firstLocationIndex = 0;
                                            return Single.just(locationEntities.get(firstLocationIndex));
                                        }
                                    });
                        }
                    });
        return dataManager.getChosenLocation();
    }

    private HourlyChartData getDataForChart(@NonNull HourlyForecastForChartViewModel viewModel) {
        int elementsNumber = 8;
        HourlyChartData hourlyChartData = new HourlyChartData();

        List<Entry> temperatureData = getTemperatureDataForChart(viewModel, elementsNumber);
        hourlyChartData.setTemperatureData(temperatureData);

        char unitSign = UnitUtils.getSign(viewModel.getTemperatureUnit());
        hourlyChartData.setUnitSign(unitSign);

        List<String> xAxisDescription = getXAxisPerHourDescription(viewModel, elementsNumber);
        hourlyChartData.setXAxisDescription(xAxisDescription);

        return hourlyChartData;

    }

    private List<Entry> getTemperatureDataForChart(HourlyForecastForChartViewModel chartForecastModel, int elementsNumber) {
        List<Entry> entries = new ArrayList<>();
        int firstElementIndex = 0;
        int maxElementIndex = --elementsNumber;

        int xAxisFakeValue = 0;
        for (Pair<Integer, String> pair : chartForecastModel.getTemperature().subList(firstElementIndex, maxElementIndex)) {
            entries.add(new Entry(xAxisFakeValue, pair.first));
            xAxisFakeValue++;
        }
        return entries;
    }

    private List<String> getXAxisPerHourDescription(HourlyForecastForChartViewModel chartForecast, int maxElements) {
        List<String> xAxisDescription = new ArrayList<>();
        int firstElementIndex = 0;
        int maxElementIndex = --maxElements;
        for (Pair<Integer, String> temperatureDatePair : chartForecast.getTemperature().subList(firstElementIndex, maxElementIndex))
            xAxisDescription.add(temperatureDatePair.second);

        return xAxisDescription;
    }
}
