package by.reshetnikov.proweather;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private Context context;

    AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    ProWeatherApp provideApp() {
        return (ProWeatherApp) context.getApplicationContext();
    }
}
