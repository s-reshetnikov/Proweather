package by.reshetnikov.proweather.di.component;

import android.app.Application;
import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import javax.inject.Singleton;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.di.module.ApplicationModule;
import by.reshetnikov.proweather.di.qualifier.ApplicationContext;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;
import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ProWeatherApp app);

    @ApplicationContext
    Context context();

    Application getApplication();

    DataContract getDataRepository();

    FirebaseJobDispatcher getFirebaseJobDispatcher();

    ThreadSchedulerProvider getThreadSchedularSchedulerProvider();

    CompositeDisposable getCompositeDisposable();


}