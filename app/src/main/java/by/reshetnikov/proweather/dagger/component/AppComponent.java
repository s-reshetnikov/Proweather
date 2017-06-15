package by.reshetnikov.proweather.dagger.component;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.currentweather.CurrentWeatherPresenter;
import by.reshetnikov.proweather.dagger.module.AppModule;
import by.reshetnikov.proweather.dagger.module.DataModule;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.db.AppLocalData;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import by.reshetnikov.proweather.weatherForecast.WeatherForecastPresenter;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    public ProWeatherApp getProWeatherApp();

    public void inject(CurrentWeatherPresenter presenter);

    public void inject(WeatherForecastPresenter presenter);

    public void inject(DataRepository dataRepository);

    public void inject(AppLocalData localData);

    public void inject(AppRemoteData remoteData);

}
