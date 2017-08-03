package by.reshetnikov.proweather.di.component;

import by.reshetnikov.proweather.ui.location.LocationActivity;
import by.reshetnikov.proweather.ui.weather.WeatherActivity;
import by.reshetnikov.proweather.ui.currentweather.CurrentWeatherFragment;
import by.reshetnikov.proweather.fragment.LocationManagerFragment;
import by.reshetnikov.proweather.di.PerActivity;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.ui.currentweather.CurrentWeatherPresenter;
import by.reshetnikov.proweather.ui.location.LocationManagerPresenter;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(WeatherActivity activity);

    void inject(LocationActivity activity);

    void inject(LocationManagerFragment fragment);

    void inject(LocationManagerPresenter presenter);

    void inject(CurrentWeatherFragment fragment);

    void inject(CurrentWeatherPresenter presenter);


}
