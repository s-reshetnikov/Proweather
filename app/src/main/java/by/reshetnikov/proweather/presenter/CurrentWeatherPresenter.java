package by.reshetnikov.proweather.presenter;

import android.content.Context;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.contract.CurrentWeatherContract;
import by.reshetnikov.proweather.data.DataRepository;
import io.reactivex.disposables.CompositeDisposable;


public class CurrentWeatherPresenter implements CurrentWeatherContract.Presenter {

    @Inject
    DataRepository dataRepository;

    private CurrentWeatherContract.View view;
    private Context appContext;
    private CompositeDisposable compositeDisposable;

    public CurrentWeatherPresenter() {
        this.appContext = ProWeatherApp.getAppContext();
        ((ProWeatherApp) appContext.getApplicationContext()).getAppComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void stop() {
        compositeDisposable.dispose();
    }

    @Override
    public void start() {
        compositeDisposable.clear();
    }

    private void getUnits() {
        dataRepository.getUnits();
    }

    @Override
    public void loadCurrentWeather() {
        getUnits();
        getWeather();
    }


    public void getWeather() {
        //CurrentWeatherModel weather = dataRepository.getCurrentWeather();
        //view.showCurrentWeather(weather);
    }

    @Override
    public void setView(CurrentWeatherContract.View view) {
        this.view = view;
    }

    public void initialize() {

    }
}
