package by.reshetnikov.proweather.di.component;

import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.di.scope.PerActivity;
import by.reshetnikov.proweather.presentation.dailyforecast.DailyForecastFragment;
import by.reshetnikov.proweather.presentation.dailyforecast.DailyForecastPresenter;
import by.reshetnikov.proweather.presentation.location.LocationActivity;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerFragment;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerPresenter;
import by.reshetnikov.proweather.presentation.location.map.MapFragment;
import by.reshetnikov.proweather.presentation.location.map.MapPresenter;
import by.reshetnikov.proweather.presentation.nowforecast.NowForecastFragment;
import by.reshetnikov.proweather.presentation.nowforecast.NowForecastPresenter;
import by.reshetnikov.proweather.presentation.weather.WeatherActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(WeatherActivity activity);

    void inject(LocationActivity activity);

    void inject(LocationManagerFragment fragment);

    void inject(LocationManagerPresenter presenter);

    void inject(MapFragment fragment);

    void inject(MapPresenter presenter);

    void inject(NowForecastFragment fragment);

    void inject(NowForecastPresenter presenter);

    void inject(DailyForecastFragment fragment);

    void inject(DailyForecastPresenter presenter);

}
