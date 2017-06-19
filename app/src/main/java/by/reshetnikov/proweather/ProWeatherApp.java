package by.reshetnikov.proweather;

import android.app.Application;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import by.reshetnikov.proweather.dagger.component.AppComponent;
import by.reshetnikov.proweather.dagger.component.DaggerAppComponent;
import by.reshetnikov.proweather.dagger.module.AppModule;
import by.reshetnikov.proweather.dagger.module.DataModule;


public class ProWeatherApp extends Application {

    private static final String TAG = ProWeatherApp.class.getSimpleName();

    private static final String baseURL = "http://api.openweathermap.org/";
    private static ProWeatherApp proWeatherApp;
    private static AppComponent appComponent;


    public static ProWeatherApp getProWeatherApp() {
        return proWeatherApp;
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
                .dataModule(new DataModule(baseURL))
                .build();

        setPreferenceDefaultValues(false);


        Log.d(TAG, "onCreate() end");

    }

    private void setPreferenceDefaultValues(boolean canReadAgain) {
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_location, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_units, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_notification, canReadAgain);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
