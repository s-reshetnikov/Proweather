package by.reshetnikov.proweather.injector.module;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.greendao.database.Database;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.db.AppDbData;
import by.reshetnikov.proweather.data.db.AppDbOpenHelper;
import by.reshetnikov.proweather.data.db.model.DaoMaster;
import by.reshetnikov.proweather.data.db.model.DaoSession;
import by.reshetnikov.proweather.data.network.AppWeatherApiData;
import by.reshetnikov.proweather.data.network.WeatherApiService;
import by.reshetnikov.proweather.data.preferences.AppSharedPreferencesData;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private static final int TIME_OUT = 3; //in seconds
    private final String baseUrl;
    private final String dbName;


    public DataModule(String baseUrl, String dbName) {
        this.baseUrl = baseUrl;
        this.dbName = dbName;
    }

    @Singleton
    @Provides
    Cache provideHttpCache(ProWeatherApp app) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(app.getCacheDir(), cacheSize);
    }

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Cache cache) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    WeatherApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(WeatherApiService.class);
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName);
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    @Singleton
    @Provides
    AppDbOpenHelper provideAppDbOpenHelper(Context context) {
        return new AppDbOpenHelper(context, dbName);
    }

    @Singleton
    @Provides
    AppDbData provideApDbData(AppDbOpenHelper openHelper) {
        return new AppDbData(openHelper);
    }

    @Singleton
    @Provides
    AppSharedPreferencesData provideSharedPreferencesData(Context context) {
        return new AppSharedPreferencesData(context);
    }

    @Singleton
    @Provides
    AppWeatherApiData provideAppWeatherApiData() {
        return new AppWeatherApiData();
    }

    @Singleton
    @Provides
    DataRepository provideDataRepository() {
        return new DataRepository();
    }
}