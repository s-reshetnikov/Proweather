package by.reshetnikov.proweather;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.concurrent.Callable;

import by.reshetnikov.proweather.di.component.ApplicationComponent;
import by.reshetnikov.proweather.di.component.DaggerApplicationComponent;
import by.reshetnikov.proweather.di.module.ApplicationModule;
import io.reactivex.Observable;


public class ProWeatherApp extends Application {

    private static final String TAG = ProWeatherApp.class.getSimpleName();
    private static ProWeatherApp proWeatherApp;
    private static ApplicationComponent applicationComponent;

    public static ProWeatherApp getProWeatherApp() {
        return proWeatherApp;
    }

    public static Context getAppContext() {
        return proWeatherApp.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "start application");

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        proWeatherApp = this;

        //TODO: move settings default values some where else
        Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                setPreferenceDefaultValues(true);
                return true;
            }
        });
        Log.d(TAG, "stop() end");
    }

    private void setPreferenceDefaultValues(boolean canReadAgain) {
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_location, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_units, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_notification, canReadAgain);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;

    }

}
