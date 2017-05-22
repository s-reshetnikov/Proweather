package by.reshetnikov.proweather;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SacRahl on 5/22/2017.
 */

@Module
public class AppModule {

    private Context context;

    AppModule(Context context) {
        this.context = context;
    }

    @Provides
    ProWeatherApp getApp() {
        return (ProWeatherApp) context.getApplicationContext();
    }

    @Provides
    Context getContext() {
        return context;
    }
}
