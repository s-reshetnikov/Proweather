package by.reshetnikov.proweather;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.preference.PreferenceManager;

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
        }

        Timber.d("start application");

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
        Timber.d("stop() end");
    }

    private void setPreferenceDefaultValues(boolean canReadAgain) {
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_location, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_units, canReadAgain);
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_notification, canReadAgain);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;

    }

//    private static class CrashReportingTree extends Timber.Tree {
//        @Override
//        protected void log(int priority, String tag, String message, Throwable t) {
//            if (priority == Log.VERBOSE || priority == Timber.dEBUG) {
//                return;
//            }
//
//            FakeCrashLibrary.log(priority, tag, message);
//
//            if (t != null) {
//                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
//                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
//                }
//            }
//        }
//    }

}
