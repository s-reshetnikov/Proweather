package by.reshetnikov.proweather;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import by.reshetnikov.proweather.data.local.dbmodels.entities.DaoMaster;
import by.reshetnikov.proweather.data.local.dbmodels.entities.DaoSession;
import by.reshetnikov.proweather.injector.component.AppComponent;
import by.reshetnikov.proweather.injector.component.DaggerAppComponent;
import by.reshetnikov.proweather.injector.module.AppModule;
import by.reshetnikov.proweather.injector.module.DataModule;


public class ProWeatherApp extends Application {

    private static final String TAG = ProWeatherApp.class.getSimpleName();

    private static final String baseURL = "http://api.openweathermap.org/";
    private static ProWeatherApp proWeatherApp;
    private static AppComponent appComponent;
    private DaoSession daoSession;


    public static ProWeatherApp getProWeatherApp() {
        return proWeatherApp;
    }

    public static Context getAppContext() {
        return proWeatherApp.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate() start");

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        proWeatherApp = this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "proweather-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, daoSession))
                .dataModule(new DataModule(baseURL))
                .build();

        SetDefaultPreferencesAsyncTask task = new SetDefaultPreferencesAsyncTask();
        task.execute();
        Log.d(TAG, "onCreate() end");
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    class SetDefaultPreferencesAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            setPreferenceDefaultValues(true);
            return null;
        }

        private void setPreferenceDefaultValues(boolean canReadAgain) {
            PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_location, canReadAgain);
            PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_units, canReadAgain);
            PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_notification, canReadAgain);
        }
    }
}
