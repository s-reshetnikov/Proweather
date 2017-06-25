package by.reshetnikov.proweather.weatherForecast;

import by.reshetnikov.proweather.data.apimodels.ForecastWeatherModels.ForecastWeather;


public class WeatherForecastPresenter implements WeatherForecastContract.Presenter {

    private WeatherForecastContract.View view;

    public WeatherForecastPresenter() {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onError() {
    }

    @Override
    public ForecastWeather getForecastWeather() {
        return null;
    }

    @Override
    public void setView(WeatherForecastContract.View view) {
        this.view = view;
    }

}
