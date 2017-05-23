package by.reshetnikov.proweather;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

public class ProWeatherApp extends Application {

    private static final String TAG = "ProWeatherApp";

    private static final String baseURL = "https://api.openweathermap.org";
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
                .networkModule(new NetworkModule(baseURL))
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
