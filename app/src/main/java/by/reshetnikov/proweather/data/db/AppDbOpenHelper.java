package by.reshetnikov.proweather.data.db;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import by.reshetnikov.proweather.data.db.model.DaoMaster;


public class AppDbOpenHelper extends DaoMaster.OpenHelper {

    private static final String TAG = AppDbOpenHelper.class.getSimpleName();

    public AppDbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d(TAG, "Database " + getDatabaseName() + ", old version is " + oldVersion + ", new version is " + newVersion);
    }


}
