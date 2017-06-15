//package by.reshetnikov.proweather.dagger.module;
//
//import android.content.Context;
//
//import javax.inject.Singleton;
//
//import by.reshetnikov.proweather.currentweather.CurrentWeatherContract;
//import by.reshetnikov.proweather.currentweather.CurrentWeatherPresenter;
//import by.reshetnikov.proweather.weatherForecast.WeatherForecastFragment;
//import dagger.Module;
//import dagger.Provides;
//
//
//@Module
//public class CurrentWeatherModule {
//
//    private Context context;
//
//    public CurrentWeatherModule(Context context) {
//        this.context = context;
//    }
//
//    @Singleton
//    @Provides
//    public CurrentWeatherPresenter getCurrentWeatherPresenter(Context context, CurrentWeatherContract.View view) {
//        return new CurrentWeatherPresenter(context, view);
//    }
//
//    @Singleton
//    @Provides
//    public WeatherForecastFragment getWeatherForecastFragment(WeatherForecastFragment fragment) {
//        return fragment;
//    }
//}
