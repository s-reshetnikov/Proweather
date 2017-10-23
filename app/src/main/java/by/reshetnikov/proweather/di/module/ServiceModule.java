package by.reshetnikov.proweather.di.module;

import android.content.Context;

import by.reshetnikov.proweather.business.nowforecast.NowForecastInteractor;
import by.reshetnikov.proweather.business.nowforecast.NowForecastInteractorContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by s-reshetnikov.
 */
@Module
public class ServiceModule {
    private Context context;

    public ServiceModule(Context context) {
        this.context = context;
    }

    @Provides
    NowForecastInteractorContract provideNowForecastInteractor(NowForecastInteractor interactor) {
        return interactor;
    }

}
