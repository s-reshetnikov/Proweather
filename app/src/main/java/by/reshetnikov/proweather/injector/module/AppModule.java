package by.reshetnikov.proweather.injector.module;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.db.model.DaoMaster;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final String dbName;
    private Context context;
    private DaoSession daoSession;

    public AppModule(Context context, String dbName) {
        this.context = context;
        this.dbName = dbName;
    }

    @Singleton
    @Provides
    ProWeatherApp provideApp() {
        return (ProWeatherApp) context.getApplicationContext();
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName);
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }
}
