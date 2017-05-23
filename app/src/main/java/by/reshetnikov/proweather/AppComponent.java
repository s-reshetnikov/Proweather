package by.reshetnikov.proweather;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
interface AppComponent {

    ProWeatherApp getProWeatherApp();

    void injectActivity(WeatherActivity weatherActivity);

}
