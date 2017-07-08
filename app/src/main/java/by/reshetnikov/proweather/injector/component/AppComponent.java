package by.reshetnikov.proweather.injector.component;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.activity.LocationActivity;
import by.reshetnikov.proweather.activity.WeatherActivity;
import by.reshetnikov.proweather.adapter.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.network.AppWeatherApiData;
import by.reshetnikov.proweather.injector.module.AppModule;
import by.reshetnikov.proweather.injector.module.DataModule;
import by.reshetnikov.proweather.presenter.CurrentWeatherPresenter;
import by.reshetnikov.proweather.presenter.LocationManagerPresenter;
import by.reshetnikov.proweather.presenter.WeatherForecastPresenter;
import by.reshetnikov.proweather.presenter.WeatherPresenter;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    ProWeatherApp getProWeatherApp();

    void inject(WeatherActivity activity);

    void inject(LocationActivity activity);

    void inject(CurrentWeatherPresenter presenter);

    void inject(WeatherPresenter presenter);

    void inject(WeatherForecastPresenter presenter);

    void inject(LocationManagerPresenter presenter);

    void inject(DataRepository dataRepository);

    void inject(AppWeatherApiData remoteData);

    void inject(AutoCompleteLocationsAdapter adapter);
}
