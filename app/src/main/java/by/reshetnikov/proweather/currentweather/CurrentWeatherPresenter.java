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
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
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
                .subscribeWith(new DisposableSingleObserver<UnitsAppModel>() {


                    @Override
                    public void onSuccess(@NonNull UnitsAppModel unitsAppModel) {
                        units = unitsAppModel;
                        Toast toast = Toast.makeText(appContext, "preferences loaded", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(CurrentWeatherPresenter.class.getSimpleName(), e.getMessage());
                    }
                }));
    }

    @Override
    public void loadCurrentWeather() {
        getUnits();
        getWeather();
    }

    public void getWeather() {
        Single<CurrentWeatherAppModel> currentWeather = dataRepository.getCurrentWeather();
        if (currentWeather == null) {
            Toast toast = Toast.makeText(appContext, "Internet connection required", Toast.LENGTH_SHORT);
            ToastUtils.showToast(toast);
            return;
        }
        compositeDisposable.add(dataRepository.getCurrentWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CurrentWeatherAppModel>() {


                    @Override
                    public void onSuccess(@NonNull CurrentWeatherAppModel appModel) {
                        Toast toast = Toast.makeText(appContext, "Current weather request completed", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                        view.showCurrentWeather(appModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(CurrentWeatherPresenter.class.getSimpleName(), e.getMessage());
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
