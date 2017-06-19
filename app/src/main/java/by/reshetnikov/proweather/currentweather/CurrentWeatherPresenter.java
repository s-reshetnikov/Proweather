package by.reshetnikov.proweather.currentweather;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class CurrentWeatherPresenter implements CurrentWeatherContract.CurrentWeatherPresenter {

    @Inject
    DataRepository dataRepository;

    private CurrentWeatherContract.View view;
    private Context context;
    private CompositeDisposable compositeDisposable;

    public CurrentWeatherPresenter(Context context) {
        this.context = context;
        ((ProWeatherApp) context.getApplicationContext()).getAppComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onError() {

    }

    @Override
    public void loadCurrentWeather() {

        compositeDisposable.add(dataRepository.getCurrentWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CurrentWeather>() {

                    @Override
                    public void onNext(@NonNull CurrentWeather currentWeatherData) {

                        Toast toast = Toast.makeText(context, "Current weather request completed", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                        CurrentWeatherAppModel currentWeather = new CurrentWeatherAppModel(currentWeatherData);
                        view.showCurrentWeather(currentWeather);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(CurrentWeatherPresenter.class.getSimpleName(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Toast toast = Toast.makeText(context, "Current weather requst completed", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                    }
                }));
    }

    @Override
    public void setView(CurrentWeatherContract.View view) {
        this.view = view;
    }
}
