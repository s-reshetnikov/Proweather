package by.reshetnikov.proweather.data.service;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
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

    @Inject
    DataContract dataManager;
    @Inject
    CompositeDisposable compositeDisposables;

    @Override
    public boolean onStartJob(JobParameters job) {
        Timber.d("on Start Job LocationService called");

        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(getBaseContext()))
                .applicationComponent(ProWeatherApp.getProWeatherApp().getComponent())
                .build()
                .inject(this);

        Timber.d("get latest coordinates called from location service");

//        getAndSaveLatestCoordinates().subscribe();
//        compositeDisposables.add(getAndSaveLatestCoordinates()
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() {
//                        Timber.d("startNowForecastService() called from location service");
//                        startNowForecastService();
//                    }
//                })
//                .doAfterTerminate(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Timber.d("Location Service finished");
//                        jobFinished(job, false);
//                    }
//                })
//                .subscribe());
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
                        Timber.d("Complete()");
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
        Timber.d("onStopJob called");
        compositeDisposables.clear();
        return false;
    }

    private Completable getAndSaveLatestCoordinates() {
        int timeout = 30;
        return dataManager.getLowPowerLastCoordinates()
                .timeout(timeout, TimeUnit.SECONDS)
                .firstOrError()
                .flatMapCompletable(new Function<Coordinates, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Coordinates coordinates) throws Exception {
                        Timber.d("try to get coordinates");
                        if (coordinates != null) {
                            Timber.i("Get coordinates: " + coordinates.getLatitude() + ", " + coordinates.getLongitude());
                            return dataManager.saveLastLocation(coordinates);
                        }
                        return Completable.never();
                    }
                });
    }

    private void startNowForecastService() {
        Timber.d("try o start NowForecastLocationBasedService service");
        GooglePlayDriver playDriver = new GooglePlayDriver(getApplicationContext());
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(playDriver);
        jobDispatcher.mustSchedule(
                jobDispatcher.newJobBuilder()
                        .setService(NowForecastService.class)
                        .setTag("NowForecastLocationBasedService")
                        .setRecurring(false)
                        .setTrigger(Trigger.NOW)
                        .setReplaceCurrent(false)
                        .build()
        );
        Timber.d("service should be started");
    }
}
