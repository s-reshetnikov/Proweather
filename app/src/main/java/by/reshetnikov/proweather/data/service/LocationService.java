package by.reshetnikov.proweather.data.service;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.model.Coordinates;
import by.reshetnikov.proweather.di.component.DaggerServiceComponent;
import by.reshetnikov.proweather.di.module.ServiceModule;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class LocationService extends JobService {

    private final String serviceTag = "ImmediateNowForecastLocationService";

    @Inject
    DataContract dataManager;
    @Inject
    CompositeDisposable compositeDisposables;
    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;

    @Override
    public boolean onStartJob(JobParameters job) {

        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(getBaseContext()))
                .applicationComponent(ProWeatherApp.getProWeatherApp().getComponent())
                .build()
                .inject(this);

        Timber.d("LocationService start()");
        compositeDisposables.add(getAndSaveLatestCoordinates()
                .subscribeOn(Schedulers.io())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        jobFinished(job, false);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Timber.d("start forecast service");
                        startNowForecastService();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof TimeoutException)
                            Timber.w("Service timeout", e);
                    }
                }));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.d("onStopJob() called");
        compositeDisposables.clear();
        firebaseJobDispatcher.cancel(serviceTag);
        return false;
    }

    private Completable getAndSaveLatestCoordinates() {
        Timber.d("getAndSaveLatestCoordinates() called");
        int requestTimeout = 60;
        return dataManager.locateCurrentPosition()
                .timeout(requestTimeout, TimeUnit.SECONDS)
                .firstOrError()
                .flatMapCompletable(new Function<Coordinates, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Coordinates coordinates) throws Exception {
                        Timber.d("Coordinates are : " + coordinates.getLatitude() + ", " + coordinates.getLongitude());
                        if (coordinates != null) {
                            return dataManager.saveLastLocation(coordinates);
                        }
                        Timber.d("coordinates NOT received");
                        return Completable.never();
                    }
                });
    }

    private void startNowForecastService() {
        firebaseJobDispatcher.mustSchedule(
                firebaseJobDispatcher.newJobBuilder()
                        .setService(NowForecastService.class)
                        .setTag(serviceTag)
                        .setRecurring(false)
                        .setTrigger(Trigger.NOW)
                        .setReplaceCurrent(false)
                        .build()
        );
    }
}
