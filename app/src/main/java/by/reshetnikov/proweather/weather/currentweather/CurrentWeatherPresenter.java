package by.reshetnikov.proweather.weather.currentweather;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.models.CurrentWetherModels.CurrentWeather;
import by.reshetnikov.proweather.utils.ToastUtils;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by SacRahl on 6/6/2017.
 */

public class CurrentWeatherPresenter implements CurrentWeatherContract.CurrentWeatherPresenter {

    @Inject
    DataRepository dataRepository;

    private CurrentWeatherContract.View view;
    private Context context;
    private CompositeDisposable compositeDisposable;

    CurrentWeatherPresenter(Context context, CurrentWeatherContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void Subscribe() {
        compositeDisposable.add(dataRepository.getCurrentWeather()
                .subscribeWith(new DisposableObserver<CurrentWeather>() {

                    @Override
                    public void onNext(@NonNull CurrentWeather currentWeather) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Toast toast = Toast.makeText(context, "Current weather requst completed", Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                    }
                }));
    }

    @Override
    public void Unsubscribe() {
        compositeDisposable.clear();
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

    }

    @Override
    public void onError() {

    }

    @Override
    public CurrentWeather getCurrentWeather(String cityId) {

        return null;
    }
}
