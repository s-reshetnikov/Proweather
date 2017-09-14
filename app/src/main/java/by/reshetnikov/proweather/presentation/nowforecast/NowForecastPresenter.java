package by.reshetnikov.proweather.presentation.nowforecast;

import android.support.v4.util.Pair;

import com.github.mikephil.charting.data.Entry;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.nowforecast.NowForecastInteractorContract;
import by.reshetnikov.proweather.data.exception.NoSavedForecastDataException;
import by.reshetnikov.proweather.data.exception.NoSavedLocationException;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.utils.UnitUtils;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;


public class NowForecastPresenter implements NowForecastContract.Presenter {


    private SchedulerProvider scheduler;
    private WeakReference<NowForecastContract.View> viewRef;
    private CompositeDisposable compositeDisposable;
    private NowForecastInteractorContract interactor;

    @Inject
    public NowForecastPresenter(NowForecastInteractorContract interactor, SchedulerProvider scheduler, CompositeDisposable disposables) {
        this.interactor = interactor;
        this.scheduler = scheduler;
        compositeDisposable = disposables;
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
    public void refreshClicked() {
        getView().showLoading();
        updateWeather();
    }

    private void updateWeather() {
        compositeDisposable.add(interactor.getForecastDataPair()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        getView().showLoading();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        getView().hideLoading();
                    }
                })
                .subscribeWith(new DisposableSingleObserver<Pair<NowForecastViewModel, HourlyForecastForChartViewModel>>() {
                    @Override
                    public void onSuccess(@NonNull Pair<NowForecastViewModel, HourlyForecastForChartViewModel> pair) {
                        getNowForecastSuccessfully(pair.first);
                        getHourlyForecastForChartSuccessfully(pair.second);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onErrorResponseReceived(e);
                    }
                }));

    }

    private void onErrorResponseReceived(Throwable exception) {
        if (exception instanceof NoSavedLocationException) {
            viewRef.get().showLocationManager();
        }
        if (exception instanceof NoSavedForecastDataException)
            viewRef.get().showTurnInternetOn();
    }

    private void getHourlyForecastForChartSuccessfully(@NonNull HourlyForecastForChartViewModel viewModel) {
        int elementsNumber = 8;
        List<Entry> temperatureData = getTemperatureDataForChart(viewModel, elementsNumber);
        char unitSign = UnitUtils.getSign(viewModel.getTemperatureUnit());
        List<String> xAxisDescription = getXAxisPerHourDescription(viewModel, elementsNumber);
        getView().updateTemperatureChartData(temperatureData, unitSign, xAxisDescription);
    }

    private void getNowForecastSuccessfully(@NonNull NowForecastViewModel nowForecastViewModel) {
        getView().showCurrentWeather(nowForecastViewModel);
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

    private NowForecastContract.View getView() {
        if (viewRef.get() != null)
            return viewRef.get();
        throw new NullPointerException("NowForecastContract.View is null!!!");
    }

    @Override
    public void setView(NowForecastContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }
}
