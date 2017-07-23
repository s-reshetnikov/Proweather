package by.reshetnikov.proweather.injector.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.injector.ApplicationContext;
import by.reshetnikov.proweather.injector.module.ApplicationModule;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ProWeatherApp app);

    @ApplicationContext
    Context context();

    Application getApplication();

    DataContract getDataRepository();
}