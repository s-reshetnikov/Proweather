package by.reshetnikov.proweather.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import by.reshetnikov.proweather.business.forecast.ForecastInteractor;
import by.reshetnikov.proweather.business.forecast.ForecastInteractorContract;
import by.reshetnikov.proweather.business.locationmanager.LocationManagerInteractor;
import by.reshetnikov.proweather.business.locationmanager.LocationManagerInteractorContract;
import by.reshetnikov.proweather.business.map.MapInteractor;
import by.reshetnikov.proweather.business.map.MapInteractorContract;
import by.reshetnikov.proweather.business.nowforecast.NowForecastInteractor;
import by.reshetnikov.proweather.business.nowforecast.NowForecastInteractorContract;
import by.reshetnikov.proweather.di.qualifier.ActivityContext;
import by.reshetnikov.proweather.presentation.dailyforecast.DailyForecastContract;
import by.reshetnikov.proweather.presentation.dailyforecast.DailyForecastPresenter;
import by.reshetnikov.proweather.presentation.location.locationmanager.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerContract;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerPresenter;
import by.reshetnikov.proweather.presentation.location.locationmanager.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.presentation.location.map.MapContract;
import by.reshetnikov.proweather.presentation.location.map.MapPresenter;
import by.reshetnikov.proweather.presentation.nowforecast.NowForecastContract;
import by.reshetnikov.proweather.presentation.nowforecast.NowForecastPresenter;
import by.reshetnikov.proweather.utils.scheduler.AppSchedulerProvider;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

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
    NowForecastContract.Presenter provideCurrentWeatherPresenter(NowForecastPresenter presenter) {
        return presenter;
    }

    @Provides
    DailyForecastContract.Presenter provideWeatherForecastPresenter(DailyForecastPresenter presenter) {
        return presenter;
    }

    @Provides
    LocationManagerContract.Presenter provideLocationManagerPresenter(LocationManagerPresenter presenter) {
        return presenter;
    }

    @Provides
    MapContract.Presenter provideMapPresenter(MapPresenter presenter) {
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

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    NowForecastInteractorContract provideNowForecastInteractor(NowForecastInteractor interactor) {
        return interactor;
    }

    @Provides
    ForecastInteractorContract provideForecastInteractor(ForecastInteractor interactor) {
        return interactor;
    }

    @Provides
    LocationManagerInteractorContract provideLocationManagerInteractor(LocationManagerInteractor interactor) {
        return interactor;
    }

    @Provides
    MapInteractorContract provideMapInteractor(MapInteractor interactor) {
        return interactor;
    }
}
