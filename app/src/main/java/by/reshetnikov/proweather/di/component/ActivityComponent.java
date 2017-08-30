package by.reshetnikov.proweather.di.component;

import by.reshetnikov.proweather.di.PerActivity;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.presentation.forecast.ForecastFragment;
import by.reshetnikov.proweather.presentation.forecast.ForecastPresenter;
import by.reshetnikov.proweather.presentation.location.LocationActivity;
import by.reshetnikov.proweather.presentation.location.LocationManagerFragment;
import by.reshetnikov.proweather.presentation.location.LocationManagerPresenter;
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

    void inject(NowForecastFragment fragment);

    void inject(NowForecastPresenter presenter);

    void inject(ForecastFragment fragment);

    void inject(ForecastPresenter presenter);


}
