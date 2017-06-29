package by.reshetnikov.proweather.dagger.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.local.AppLocalData;
import by.reshetnikov.proweather.data.local.db.entities.DaoSession;
import by.reshetnikov.proweather.data.remote.AppRemoteData;
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
    private String baseUrl;

    public DataModule(String baseUrl) {
        this.baseUrl = baseUrl;
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
    AppLocalData provideLocalData(ProWeatherApp context, DaoSession daoSession) {
        return new AppLocalData(context, daoSession);
    }

    @Singleton
    @Provides
    AppRemoteData provideAppRemoteData() {
        return new AppRemoteData();
    }

    @Singleton
    @Provides
    DataRepository provideDataRepository() {
        return new DataRepository();
    }
}