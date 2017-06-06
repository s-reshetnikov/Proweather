package by.reshetnikov.proweather.weather.currentweather;

import by.reshetnikov.proweather.BasePresenter;
import by.reshetnikov.proweather.BaseView;

/**
 * Created by SacRahl on 6/5/2017.
 */

public interface CurrentWeatherContract {

    interface View extends BaseView<CurrentWeatherPresenter> {

        void showCurrentWeather();
    }

    interface CurrentWeatherPresenter extends BasePresenter {
        void getCurrentWeather(String cityId);
    }
}
