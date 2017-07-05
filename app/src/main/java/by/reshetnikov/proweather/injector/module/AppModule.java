package by.reshetnikov.proweather.injector.module;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.model.dbmodels.DaoMaster;
import by.reshetnikov.proweather.model.dbmodels.DaoSession;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;
    private DaoSession daoSession;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    ProWeatherApp provideApp() {
        return (ProWeatherApp) context.getApplicationContext();
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "proweather-db");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }
}
