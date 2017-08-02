package by.reshetnikov.proweather.injector.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import by.reshetnikov.proweather.adapter.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.contract.CurrentWeatherContract;
import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.contract.WeatherForecastContract;
import by.reshetnikov.proweather.injector.ActivityContext;
import by.reshetnikov.proweather.presenter.CurrentWeatherPresenter;
import by.reshetnikov.proweather.presenter.LocationManagerPresenter;
import by.reshetnikov.proweather.presenter.WeatherForecastPresenter;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import by.reshetnikov.proweather.utils.scheduler.AppSchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    @ActivityContext
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    CurrentWeatherContract.Presenter provideCurrentWeatherPresenter(CurrentWeatherPresenter presenter) {
        return presenter;
    }

    @Provides
    WeatherForecastContract.Presenter provideWeatherForecastPresenter(WeatherForecastPresenter presenter) {
        return presenter;
    }

    @Provides
    LocationManagerContract.Presenter provideLocationManagerPresenter(LocationManagerPresenter presenter) {
        return presenter;
    }

    @Provides
    @ActivityContext
    AutoCompleteLocationsAdapter provideAutoCompleteLocationsAdapter(Context context) {
        return new AutoCompleteLocationsAdapter(context);
    }

    @Provides
    LocationsRecyclerViewAdapter provideLocationsRecyclerViewAdapter() {
        return new LocationsRecyclerViewAdapter();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
