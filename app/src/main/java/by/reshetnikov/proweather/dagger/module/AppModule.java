package by.reshetnikov.proweather.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.local.db.entities.DaoSession;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;
    private DaoSession daoSession;

    public AppModule(Context context, DaoSession daoSession) {
        this.context = context;
        this.daoSession = daoSession;
    }

    @Singleton
    @Provides
    ProWeatherApp provideApp() {
        return (ProWeatherApp) context.getApplicationContext();
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        return daoSession;
    }
}
