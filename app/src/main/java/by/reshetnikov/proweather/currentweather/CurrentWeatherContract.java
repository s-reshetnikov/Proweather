package by.reshetnikov.proweather.currentweather;


import by.reshetnikov.proweather.BasePresenter;
import by.reshetnikov.proweather.BaseView;
import by.reshetnikov.proweather.data.apimodels.CurrentWeatherModels.CurrentWeather;

/**
 * Created by SacRahl on 6/5/2017.
 */

public interface CurrentWeatherContract {

    interface View extends BaseView<CurrentWeatherPresenter> {

        void showCurrentWeather(CurrentWeather currentWeather);
    }

    interface CurrentWeatherPresenter extends BasePresenter {
        void loadCurrentWeather();

        void setView(CurrentWeatherContract.View view);
    }
}