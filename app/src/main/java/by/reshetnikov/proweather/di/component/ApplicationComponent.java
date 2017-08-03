package by.reshetnikov.proweather.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.di.ApplicationContext;
import by.reshetnikov.proweather.di.module.ApplicationModule;
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