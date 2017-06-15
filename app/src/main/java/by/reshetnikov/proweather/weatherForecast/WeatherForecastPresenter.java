package by.reshetnikov.proweather.weatherForecast;

import android.content.Context;

import by.reshetnikov.proweather.weather.WeatherContract;

/**
 * Created by SacRahl on 6/12/2017.
 */

public class WeatherForecastPresenter implements WeatherContract.WeatherPresenter {

    private Context context;
    private WeatherForecastFragment view;

    public WeatherForecastPresenter(Context context, WeatherForecastContract.View view) {
        this.context = context;
        this.view = (WeatherForecastFragment) view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
    }

    @Override
    public void onError() {

    }

    @Override
    public void getCitiesList() {

    }

    @Override
    public void setView(WeatherContract.View view) {

    }
}
