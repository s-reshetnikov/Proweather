package by.reshetnikov.proweather.presentation.dailyforecast;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.forecast.ForecastInteractor;
import by.reshetnikov.proweather.business.forecast.ForecastInteractorContract;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;


public class DailyForecastPresenter implements DailyForecastContract.Presenter {

    private WeakReference<DailyForecastContract.View> viewRef;
    private ForecastInteractorContract interactor;
    private ThreadSchedulerProvider scheduler;
    private CompositeDisposable disposables;

    @Inject
    public DailyForecastPresenter(ForecastInteractor interactor, ThreadSchedulerProvider scheduler, CompositeDisposable disposables) {
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
                .subscribeOn(scheduler.ui())
                .observeOn(scheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        getView().showLoading();
                    }
                })
                .subscribeOn(scheduler.ui())
                .observeOn(scheduler.ui())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() {
                        getView().hideLoading();
                    }
                })
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<List<DailyForecastViewModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<DailyForecastViewModel> forecast) {
                        getView().showWeatherForecast(forecast);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e);
                    }
                }));
    }

    private DailyForecastContract.View getView() {
        if (viewRef.get() != null)
            return viewRef.get();

        throw new NullPointerException("View disposed in presenter!");
    }

    @Override
    public void setView(DailyForecastContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }
}
