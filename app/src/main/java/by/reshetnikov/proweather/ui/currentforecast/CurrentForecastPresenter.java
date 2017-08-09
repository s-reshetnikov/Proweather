package by.reshetnikov.proweather.ui.currentforecast;

import com.github.mikephil.charting.data.Entry;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.model.unit.UnitsAppModel;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapterContract;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;


public class CurrentForecastPresenter implements CurrentForecastContract.Presenter {

    private DataContract dataRepository;
    private SchedulerProvider scheduler;
    private WeakReference<CurrentForecastContract.View> viewRef;
    private CompositeDisposable compositeDisposable;
    private UnitsAppModel units;

    @Inject
    public CurrentForecastPresenter(DataContract dataRepository, SchedulerProvider scheduler) {
        this.dataRepository = dataRepository;
        this.scheduler = scheduler;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {
        compositeDisposable.clear();
    }

    @Override
    public void stop() {
        compositeDisposable.dispose();
    }

    @Override
    public void loadCurrentWeather() {
        getUnits();
//        updateWeather();
        updateChart();
    }

//    private Location getCurrentLocationId(){
//        compositeDisposable.add(dataRepository.getChosenLocation()
//        .subscribeOn(scheduler.io())
//        .observeOn(scheduler.ui())
//        .subscribeWith(new DisposableObserver<LocationContract>() {
//            @Override
//            public void onNext(@NonNull LocationContract locationAdapterContract) {
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }));
//    }

    private void updateChart() {
//        compositeDisposable.add(dataRepository.getSavedHourlyForecastWeather());
    }

//    private void updateWeather() {
//        compositeDisposable.add(dataRepository.getSavedCurrentWeather(new LocationAdapter())
//                .subscribeOn(scheduler.io())
//                .observeOn(scheduler.ui())
//                .subscribeWith(new DisposableSingleObserver<CurrentForecastAdapterContract>() {
//
//                    @Override
//                    public void onSuccess(@NonNull CurrentForecastAdapterContract currentForecast) {
//                        getView().showCurrentWeather(currentForecast);
//                        List<Entry> data = getDataForChart(currentForecast);
//                        getView().updateTimeLineData(data);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                }));
//    }


    private List<Entry> getDataForChart(CurrentForecastAdapterContract currentForecast) {
        return new ArrayList<Entry>();
    }

    private void getUnits() {
//        units = dataRepository.getUnits();
    }

    private CurrentForecastContract.View getView() {
        if (viewRef.get() != null)
            return viewRef.get();
        throw new NullPointerException("CurrentForecastContract.View is null!!!");
    }

    @Override
    public void setView(CurrentForecastContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }
}
