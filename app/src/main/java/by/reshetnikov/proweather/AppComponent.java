package by.reshetnikov.proweather;

import android.content.Context;

import dagger.Component;

/**
 * Created by SacRahl on 5/22/2017.
 */

@Component(modules = AppModule.class)
public interface AppComponent {

    ProWeatherApp getProWeatherApp();

    Context getContext();

}
