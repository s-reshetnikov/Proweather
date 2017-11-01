package by.reshetnikov.proweather.business.nowforecastservice;

import android.support.v4.util.Pair;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.db.model.NowForecastEntity;
import by.reshetnikov.proweather.data.exception.NoLocationException;
import by.reshetnikov.proweather.data.model.Coordinates;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class ServiceForecastInteractor implements ServiceForecastInteractorContract {

    private DataContract dataManager;
    private ThreadSchedulerProvider scheduler;

    @Inject
    public ServiceForecastInteractor(DataContract dataManager, ThreadSchedulerProvider scheduler) {
        this.dataManager = dataManager;
        this.scheduler = scheduler;
    }

    @Override
    public Single<NowForecastViewModel> getNowForecastData() {
        return getLocation()
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

    private Single<LocationEntity> getLocation() {
        int attemptsNumber = 3;
        if (dataManager.canUseCurrentLocation() && dataManager.canGetLatestLocation())
            return dataManager.getLastSavedLocation()
                    .flatMap(new Function<Coordinates, SingleSource<LocationEntity>>() {
                        @Override
                        public SingleSource<LocationEntity> apply(Coordinates coordinates) throws Exception {
                            int resultsCount = 1;
                            return dataManager.getLocationsByCoordinates(coordinates.getLatitude(), coordinates.getLongitude(), resultsCount)
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
                    })
                    .retry(attemptsNumber)
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (throwable instanceof NoLocationException)
                                Timber.w(throwable);
                            else
                                Timber.e(throwable);
                        }
                    });
        return dataManager.getChosenLocation();
    }
}
