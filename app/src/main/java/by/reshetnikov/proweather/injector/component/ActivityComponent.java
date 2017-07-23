package by.reshetnikov.proweather.injector.component;

import by.reshetnikov.proweather.activity.LocationActivity;
import by.reshetnikov.proweather.activity.WeatherActivity;
import by.reshetnikov.proweather.fragment.LocationManagerFragment;
import by.reshetnikov.proweather.injector.PerActivity;
import by.reshetnikov.proweather.injector.module.ActivityModule;
import by.reshetnikov.proweather.presenter.CurrentWeatherPresenter;
import by.reshetnikov.proweather.presenter.LocationManagerPresenter;
import by.reshetnikov.proweather.presenter.WeatherForecastPresenter;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

//    void inject(WeatherActivity activity);

    void inject(LocationActivity activity);

    void inject(LocationManagerFragment fragment);

//    void inject(LocationManagerPresenter presenter);
//
//    void inject(CurrentWeatherPresenter presenter);
}
