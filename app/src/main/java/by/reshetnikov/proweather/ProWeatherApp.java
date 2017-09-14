package by.reshetnikov.proweather;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.concurrent.Callable;

import by.reshetnikov.proweather.di.component.ApplicationComponent;
import by.reshetnikov.proweather.di.component.DaggerApplicationComponent;
import by.reshetnikov.proweather.di.module.ApplicationModule;
import io.fabric.sdk.android.Fabric;
import io.reactivex.Observable;
import timber.log.Timber;


public class ProWeatherApp extends Application {

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

        Fabric.with(this, new Crashlytics());


        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        Fabric.getLogger().i("Test", "for test");

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
    }

    private void setPreferenceDefaultValues(boolean canReadAgain) {
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_location, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_units, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_notification, canReadAgain);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;

    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable throwable) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG)
                return;

            if (priority == Log.INFO) {
                Fabric.getLogger().i(tag, message);
                return;
            }

            if (throwable != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.logException(throwable);
                } else if (priority == Log.WARN) {
                    Crashlytics.log(Log.WARN, tag, message);
                }
            }
        }
    }

}
