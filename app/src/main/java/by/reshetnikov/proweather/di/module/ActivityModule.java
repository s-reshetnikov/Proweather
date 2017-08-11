package by.reshetnikov.proweather.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import by.reshetnikov.proweather.di.ActivityContext;
import by.reshetnikov.proweather.ui.currentforecast.CurrentForecastContract;
import by.reshetnikov.proweather.ui.currentforecast.CurrentForecastPresenter;
import by.reshetnikov.proweather.ui.forecast.ForecastContract;
import by.reshetnikov.proweather.ui.forecast.ForecastPresenter;
import by.reshetnikov.proweather.ui.location.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.ui.location.LocationManagerContract;
import by.reshetnikov.proweather.ui.location.LocationManagerPresenter;
import by.reshetnikov.proweather.ui.location.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.utils.scheduler.AppSchedulerProvider;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
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
    CurrentForecastContract.Presenter provideCurrentWeatherPresenter(CurrentForecastPresenter presenter) {
        return presenter;
    }

    @Provides
    ForecastContract.Presenter provideWeatherForecastPresenter(ForecastPresenter presenter) {
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
