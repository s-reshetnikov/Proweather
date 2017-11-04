package by.reshetnikov.proweather.di.module;

import android.app.Application;
import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.DataManager;
import by.reshetnikov.proweather.data.db.AppDbData;
import by.reshetnikov.proweather.data.db.AppDbOpenHelper;
import by.reshetnikov.proweather.data.db.DbContract;
import by.reshetnikov.proweather.data.db.model.DaoMaster;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.network.AppWeatherApiData;
import by.reshetnikov.proweather.data.network.WeatherApiDataContract;
import by.reshetnikov.proweather.data.network.openweathermap.OpenWeatherMapApiService;
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import by.reshetnikov.proweather.data.preferences.PreferencesContract;
import by.reshetnikov.proweather.data.service.LocationService;
import by.reshetnikov.proweather.data.service.NowForecastService;
import by.reshetnikov.proweather.di.qualifier.ApplicationContext;
import by.reshetnikov.proweather.di.qualifier.DatabaseInfo;
import by.reshetnikov.proweather.di.qualifier.job.ImmediateForecast;
import by.reshetnikov.proweather.di.qualifier.job.ImmediateLocation;
import by.reshetnikov.proweather.di.qualifier.job.IntervalForecast;
import by.reshetnikov.proweather.di.qualifier.job.IntervalLocation;
import by.reshetnikov.proweather.di.qualifier.locationrequest.BalancedPowerAccuracy;
import by.reshetnikov.proweather.di.qualifier.locationrequest.LowPower;
import by.reshetnikov.proweather.utils.AppConstants;
import by.reshetnikov.proweather.utils.scheduler.AppThreadSchedulerProvider;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    ThreadSchedulerProvider provideSchedulerProvider() {
        return new AppThreadSchedulerProvider();
    }

    // network
    @Provides
    Cache provideHttpCache() {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    OkHttpClient provideOkHttpClient(Cache cache) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.OPEN_WEATHER_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    OpenWeatherMapApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(OpenWeatherMapApiService.class);
    }

    // db
    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    DaoSession provideDaoSession(AppDbOpenHelper dbHelper) {
        return new DaoMaster(dbHelper.getWritableDb()).newSession();
    }

    @Provides
    DaoMaster.OpenHelper provideAppDbOpenHelper(AppDbOpenHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    DbContract provideDbContract(AppDbData dbData) {
        return dbData;
    }

    @Provides
    WeatherApiDataContract provideWeatherApiDataContract(AppWeatherApiData api) {
        return api;
    }

    // shared preferences
    @Provides
    PreferencesContract provideSharedPreferences(@ApplicationContext Context context) {
        return new AppSharedPreferencesData(context);
    }

    // repository
    @Singleton
    @Provides
    DataContract provideDataRepository(DataManager repository) {
        return repository;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    //rxLocation
    @Provides
    @LowPower
    LocationRequest provideLowPowerLocationRequest() {
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setFastestInterval(AppConstants.LOCATION_LOW_POWER_FASTEST_INTERVAL)
                .setInterval(AppConstants.LOCATION_LOW_POWER_SLOWEST_INTERVAL);
    }

    @Provides
    @BalancedPowerAccuracy
    LocationRequest provideBalancedPowerAccuracyLocationRequest() {
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setFastestInterval(AppConstants.LOCATION_BALANCED_POWER_FASTEST_INTERVAL)
                .setInterval(AppConstants.LOCATION_BALANCED_POWER_SLOWEST_INTERVAL);
    }

    @Provides
    @Singleton
    ReactiveLocationProvider provideRxLocation(@ApplicationContext Context context) {
        return new ReactiveLocationProvider(context);
    }

    @Provides
    @Singleton
    GooglePlayDriver provideGooglePlayDriver(@ApplicationContext Context context) {
        return new GooglePlayDriver(context);
    }

    @Provides
    @Singleton
    FirebaseJobDispatcher provideFirebaseJobDispatcher(GooglePlayDriver googlePlayDriver) {
        return new FirebaseJobDispatcher(googlePlayDriver);
    }

    @Provides
    @ImmediateLocation
    Job provideImmediateLocationJob(FirebaseJobDispatcher firebaseJobDispatcher) {
        return firebaseJobDispatcher.newJobBuilder()
                .setService(LocationService.class)
                .setTag("ImmediateLocationJob")
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(AppConstants.LOCATION_SERVICE_WINDOW_START,
                        AppConstants.LOCATION_SERVICE_WINDOW_END))
                .setReplaceCurrent(true)
                .addConstraint(Constraint.ON_ANY_NETWORK)
                .build();
    }

    @Provides
    @IntervalLocation
    Job provideIntervalLocationJob(FirebaseJobDispatcher firebaseJobDispatcher) {
        return firebaseJobDispatcher.newJobBuilder()
                .setService(LocationService.class)
                .setTag("IntervalLocationJob")
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(AppConstants.LOCATION_SERVICE_WINDOW_START,
                        AppConstants.LOCATION_SERVICE_WINDOW_END))
                .setReplaceCurrent(true)
                .addConstraint(Constraint.ON_ANY_NETWORK)
                .build();
    }

    @Provides
    @ImmediateForecast
    Job provideImmediateForecastJob(FirebaseJobDispatcher firebaseJobDispatcher) {
        return firebaseJobDispatcher.newJobBuilder()
                .setService(NowForecastService.class)
                .setTag("ImmediateNowForecastJob")
                .setRecurring(false)
                .setTrigger(Trigger.NOW)
                .setReplaceCurrent(true)
                .build();
    }

    @Provides
    @IntervalForecast
    Job provideIntervalForecastJob(FirebaseJobDispatcher firebaseJobDispatcher) {
        return firebaseJobDispatcher.newJobBuilder()
                .setService(NowForecastService.class)
                .setTag("IntervalNowForecastJob")
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(AppConstants.FORECAST_SERVICE_WINDOW_START,
                        AppConstants.FORECAST_SERVICE_WINDOW_END))
                .setReplaceCurrent(true)
                .addConstraint(Constraint.ON_ANY_NETWORK)
                .build();
    }
}