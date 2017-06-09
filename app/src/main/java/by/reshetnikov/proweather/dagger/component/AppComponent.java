package by.reshetnikov.proweather.dagger.component;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.dagger.module.AppModule;
import by.reshetnikov.proweather.dagger.module.DataModule;
import by.reshetnikov.proweather.data.db.AppLocalData;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    public ProWeatherApp getProWeatherApp();

    public void injectLocalData(AppLocalData localData);

    public void injectRemoteData(AppRemoteData localData);

}
