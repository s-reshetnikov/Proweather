package by.reshetnikov.proweather.presentation.nowforecast;


import android.support.v4.util.Pair;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.nowforecast.NowForecastInteractorContract;
import by.reshetnikov.proweather.data.exception.NoLocationException;
import by.reshetnikov.proweather.data.exception.NoSavedForecastDataException;
import by.reshetnikov.proweather.data.model.weather.nowforecast.HourlyChartData;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;


public class NowForecastPresenter implements NowForecastContract.Presenter {


    private ThreadSchedulerProvider scheduler;
    private WeakReference<NowForecastContract.View> viewRef;
    private CompositeDisposable compositeDisposables;
    private NowForecastInteractorContract interactor;

    @Inject
    public NowForecastPresenter(NowForecastInteractorContract interactor, ThreadSchedulerProvider scheduler, CompositeDisposable disposables) {
        this.interactor = interactor;
        this.scheduler = scheduler;
        compositeDisposables = disposables;
    }

    @Override
    public void start() {
        updateWeather();
    }

    @Override
    public void stop() {
        compositeDisposables.clear();
    }

    @Override
    public void swipedToRefresh() {
        updateWeather();
    }

    private void updateWeather() {
        compositeDisposables.add(interactor.getForecasts()
                .subscribeOn(scheduler.ui())
                .observeOn(scheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        getView().showLoading();
                    }
                })
                .subscribeOn(scheduler.ui())
                .observeOn(scheduler.ui())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getView().hideLoading();
                    }
                })
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<Pair<NowForecastViewModel, HourlyChartData>>() {
                    @Override
                    public void onSuccess(Pair<NowForecastViewModel, HourlyChartData> pair) {
                        getView().showCurrentWeather(pair.first);
                        getView().updateTemperatureChartData(pair.second);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        if (e instanceof NoSavedForecastDataException)
                            getView().showTurnInternetOn();
                        if (e instanceof NoLocationException)
                            getView().showLocationManager();
                    }
                }));
    }

    private Single<NowForecastViewModel> getNowForecastSubscription() {
        return interactor.getNowForecastData()
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
                });
    }

    private Single<HourlyChartData> getChartDataSubscription() {
        return interactor.getDataForChart();

    }

    private void onErrorResponseReceived(Throwable exception) {
        if (exception instanceof NoLocationException) {
            getView().showLocationManager();
        }
        if (exception instanceof NoSavedForecastDataException)
            getView().showTurnInternetOn();
    }

    private NowForecastContract.View getView() {
        if (viewRef.get() != null)
            return viewRef.get();
        throw new NullPointerException("Couldn't get view for presenter!");
    }

    @Override
    public void setView(NowForecastContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }
}
