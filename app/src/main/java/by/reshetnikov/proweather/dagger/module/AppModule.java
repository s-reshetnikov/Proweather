package by.reshetnikov.proweather.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    ProWeatherApp provideApp() {
        return (ProWeatherApp) context.getApplicationContext();
    }
}
