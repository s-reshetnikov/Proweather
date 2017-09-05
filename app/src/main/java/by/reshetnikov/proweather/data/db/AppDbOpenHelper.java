package by.reshetnikov.proweather.data.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.db.model.DaoMaster;
import by.reshetnikov.proweather.di.ApplicationContext;
import by.reshetnikov.proweather.di.DatabaseInfo;
import timber.log.Timber;


public class AppDbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public AppDbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String dbName) {
        super(context, dbName);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Timber.d("Database " + getDatabaseName() + ", old version is " + oldVersion + ", new version is " + newVersion);
    }
}