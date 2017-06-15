//package by.reshetnikov.proweather.dagger.module;
//
//import android.content.Context;
//
//import javax.inject.Singleton;
//
//import by.reshetnikov.proweather.weatherForecast.WeatherForecastContract;
//import by.reshetnikov.proweather.weatherForecast.WeatherForecastFragment;
//import by.reshetnikov.proweather.weatherForecast.WeatherForecastPresenter;
//import dagger.Module;
//import dagger.Provides;
//
//@Module
//public class WeatherForecastModule {
//
//    @Singleton
//    @Provides
//    WeatherForecastPresenter getWeatherForecastPresenter(Context context, WeatherForecastContract.View view) {
//        return new WeatherForecastPresenter(context, view);
//    }
//
//    @Singleton
//    @Provides
//    WeatherForecastFragment getWeatherForecastFragment(WeatherForecastFragment fragment) {
//        return fragment;
//    }
//}
