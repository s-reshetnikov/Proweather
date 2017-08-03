package by.reshetnikov.proweather.ui.currentweather;

import javax.inject.Inject;

import by.reshetnikov.proweather.ui.currentweather.CurrentWeatherContract;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;


public class CurrentWeatherPresenter implements CurrentWeatherContract.Presenter {

    private DataContract dataRepository;
    private SchedulerProvider scheduler;
    private CurrentWeatherContract.View view;
    private CompositeDisposable compositeDisposable;

    @Inject
    public CurrentWeatherPresenter(DataContract dataRepository, SchedulerProvider scheduler) {
        this.dataRepository = dataRepository;
        this.scheduler = scheduler;
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
    }

    @Override
    public void setView(CurrentWeatherContract.View view) {
        this.view = view;
    }

    public void initialize() {

    }
}
