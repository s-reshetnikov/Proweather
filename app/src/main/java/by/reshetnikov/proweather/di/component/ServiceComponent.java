package by.reshetnikov.proweather.di.component;

import by.reshetnikov.proweather.data.service.LocationService;
import by.reshetnikov.proweather.data.service.NowForecastService;
import by.reshetnikov.proweather.di.module.ServiceModule;
import by.reshetnikov.proweather.di.scope.ServiceScope;
import dagger.Component;

/**
 * Created by s-reshetnikov.
 */
@ServiceScope
@Component(dependencies = {ApplicationComponent.class}, modules = {ServiceModule.class})
public interface ServiceComponent {

    void inject(NowForecastService service);

    void inject(LocationService service);
}
