package by.reshetnikov.proweather.injector.module;

import android.content.Context;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    ProWeatherApp provideApp() {
        return (ProWeatherApp) context.getApplicationContext();
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

}
