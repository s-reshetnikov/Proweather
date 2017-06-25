package by.reshetnikov.proweather.currentweather;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.data.appmodels.UnitsAppModel;
import by.reshetnikov.proweather.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class CurrentWeatherPresenter implements CurrentWeatherContract.Presenter {

    @Inject
    DataRepository dataRepository;

    private CurrentWeatherContract.View view;
    private Context appContext;
    private CurrentWeatherAppModel currentWeather;
    private UnitsAppModel units;
    private CompositeDisposable compositeDisposable;

    public CurrentWeatherPresenter() {
        this.appContext = ProWeatherApp.getAppContext();
        ((ProWeatherApp) appContext.getApplicationContext()).getAppComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onError() {

    }

    private void getUnits() {
        compositeDisposable.add(dataRepository.getUnits()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UnitsAppModel>() {

                    @Override
                    public void onNext(@NonNull UnitsAppModel unitsModel) {
                        units = unitsModel;
                        Toast toast = Toast.makeText(appContext, "preferences loaded", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(CurrentWeatherPresenter.class.getSimpleName(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Toast toast = Toast.makeText(appContext, "Units completed", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                    }
                }));
    }

    @Override
    public void loadCurrentWeather() {
        getUnits();
        getWeather();
    }

    public void getWeather() {
        compositeDisposable.add(dataRepository.getCurrentWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CurrentWeatherAppModel>() {

                    @Override
                    public void onNext(@NonNull CurrentWeatherAppModel currentWeatherAppModel) {
                        Toast toast = Toast.makeText(appContext, "Current weather request completed", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                        view.showCurrentWeather(currentWeatherAppModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(CurrentWeatherPresenter.class.getSimpleName(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Toast toast = Toast.makeText(appContext, "Current weather request completed", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                    }
                }));
    }

    @Override
    public void setView(CurrentWeatherContract.View view) {
        this.view = view;
    }

    public void initialize() {

    }
}
