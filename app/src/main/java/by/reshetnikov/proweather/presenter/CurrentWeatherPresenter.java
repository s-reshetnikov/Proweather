package by.reshetnikov.proweather.presenter;

import android.content.Context;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.contract.CurrentWeatherContract;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;
import by.reshetnikov.proweather.model.appmodels.UnitsAppModel;
import io.reactivex.disposables.CompositeDisposable;


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
        dataRepository.getUnits();
    }

    @Override
    public void loadCurrentWeather() {
        getUnits();
        getWeather();
    }


    public void getWeather() {
        //CurrentWeatherAppModel weather = dataRepository.getCurrentWeather();
        //view.showCurrentWeather(weather);
    }

    @Override
    public void setView(CurrentWeatherContract.View view) {
        this.view = view;
    }

    public void initialize() {

    }
}
