package by.reshetnikov.proweather;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import by.reshetnikov.proweather.injector.component.AppComponent;
import by.reshetnikov.proweather.injector.component.DaggerAppComponent;
import by.reshetnikov.proweather.injector.module.AppModule;
import by.reshetnikov.proweather.injector.module.DataModule;


public class ProWeatherApp extends Application {

    private static final String TAG = ProWeatherApp.class.getSimpleName();

    private static final String baseURL = "http://api.openweathermap.org/";
    private static final String dbName = "proweatherDb";
    private static ProWeatherApp proWeatherApp;
    private static AppComponent appComponent;


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

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule(baseURL, dbName))
                .build();

        SetDefaultPreferencesAsyncTask task = new SetDefaultPreferencesAsyncTask();
        task.execute();
        Log.d(TAG, "onCreate() end");
    }


    public AppComponent getAppComponent() {
        return appComponent;
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
