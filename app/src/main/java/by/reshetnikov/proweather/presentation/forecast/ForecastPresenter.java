package by.reshetnikov.proweather.presentation.forecast;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.forecast.ForecastInteractor;
import by.reshetnikov.proweather.business.forecast.ForecastInteractorContract;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;


public class ForecastPresenter implements ForecastContract.Presenter {

    private WeakReference<ForecastContract.View> viewRef;
    private ForecastInteractorContract interactor;
    private SchedulerProvider scheduler;
    private CompositeDisposable disposables;

    @Inject
    public ForecastPresenter(ForecastInteractor interactor, SchedulerProvider scheduler, CompositeDisposable disposables) {
        this.interactor = interactor;
        this.disposables = disposables;
        this.scheduler = scheduler;
    }

    @Override
    public void stop() {
        disposables.clear();
        viewRef.clear();
    }

    @Override
    public void start() {
        getForecast();
    }

    @Override
    public void onRefresh() {
        getForecast();
    }

    private void getForecast() {
        getView().showLoading();
        disposables.add(interactor.getForecasts()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<List<DailyForecastViewModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<DailyForecastViewModel> forecast) {
                        Log.i("ForecastPresenter", "size is " + forecast.size());
                        getView().showWeatherForecast(forecast);
                        getView().hideLoading();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("ForecastPresenter", "getForecast()", e);
                        getView().onError(e.getMessage());
                        getView().hideLoading();
                    }
                }));
    }

    private ForecastContract.View getView() {
        if (viewRef.get() != null)
            return viewRef.get();
        throw new NullPointerException("View disposed in presenter!");
    }

    @Override
    public void setView(ForecastContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }
}
