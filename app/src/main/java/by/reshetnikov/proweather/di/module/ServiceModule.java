package by.reshetnikov.proweather.di.module;

import android.content.Context;

import by.reshetnikov.proweather.business.nowforecastservice.ServiceForecastInteractor;
import by.reshetnikov.proweather.business.nowforecastservice.ServiceForecastInteractorContract;
import by.reshetnikov.proweather.di.qualifier.ServiceContext;
import dagger.Module;
import dagger.Provides;

/**
 * Created by s-reshetnikov.
 */
@Module
public class ServiceModule {
    @ServiceContext
    private Context context;

    public ServiceModule(Context context) {
        this.context = context;
    }

    @ServiceContext
    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    ServiceForecastInteractorContract provideNowForecastInteractor(ServiceForecastInteractor interactor) {
        return interactor;
    }
}
