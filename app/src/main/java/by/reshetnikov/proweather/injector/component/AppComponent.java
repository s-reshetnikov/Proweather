package by.reshetnikov.proweather.injector.component;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.activity.WeatherActivity;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.local.AppLocalData;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import by.reshetnikov.proweather.injector.module.AppModule;
import by.reshetnikov.proweather.injector.module.DataModule;
import by.reshetnikov.proweather.presenter.CurrentWeatherPresenter;
import by.reshetnikov.proweather.presenter.WeatherForecastPresenter;
import by.reshetnikov.proweather.presenter.WeatherPresenter;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    public ProWeatherApp getProWeatherApp();

    public void inject(WeatherActivity activity);

    public void inject(WeatherPresenter presenter);

    public void inject(CurrentWeatherPresenter currentWeatherPresenter);

    public void inject(WeatherForecastPresenter presenter);

    public void inject(DataRepository dataRepository);

    public void inject(AppLocalData localData);

    public void inject(AppRemoteData remoteData);
}
