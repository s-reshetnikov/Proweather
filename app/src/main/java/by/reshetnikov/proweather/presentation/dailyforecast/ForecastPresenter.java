package by.reshetnikov.proweather.presentation.dailyforecast;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.forecast.ForecastInteractor;
import by.reshetnikov.proweather.business.forecast.ForecastInteractorContract;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;


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
        disposables.add(interactor.getForecasts()
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
                .subscribeOn(scheduler.io())
                .subscribeWith(new DisposableSingleObserver<List<DailyForecastViewModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<DailyForecastViewModel> forecast) {
                        getView().showWeatherForecast(forecast);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e("ForecastPresenter", "getForecast()", e);
                        getView().onError("Forecast loading failed");
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
