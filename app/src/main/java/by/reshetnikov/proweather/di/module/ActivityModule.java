package by.reshetnikov.proweather.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import by.reshetnikov.proweather.ui.hourlyforecast.HourlyForecastPresenter;
import by.reshetnikov.proweather.ui.location.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.ui.location.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.ui.currentweather.CurrentWeatherContract;
import by.reshetnikov.proweather.ui.location.LocationManagerContract;
import by.reshetnikov.proweather.ui.hourlyforecast.HourlyForecastContract;
import by.reshetnikov.proweather.di.ActivityContext;
import by.reshetnikov.proweather.ui.currentweather.CurrentWeatherPresenter;
import by.reshetnikov.proweather.ui.location.LocationManagerPresenter;
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
    HourlyForecastContract.Presenter provideWeatherForecastPresenter(HourlyForecastPresenter presenter) {
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
