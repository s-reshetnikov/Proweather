package by.reshetnikov.proweather.presenter;

import javax.inject.Inject;

import by.reshetnikov.proweather.contract.CurrentWeatherContract;
import by.reshetnikov.proweather.data.DataRepository;
import io.reactivex.disposables.CompositeDisposable;


public class CurrentWeatherPresenter implements CurrentWeatherContract.Presenter {

    private DataRepository dataRepository;

    private CurrentWeatherContract.View view;
    private CompositeDisposable compositeDisposable;

    @Inject
    public CurrentWeatherPresenter(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
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
