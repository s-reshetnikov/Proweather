package by.reshetnikov.proweather.currentweather;


import by.reshetnikov.proweather.BasePresenter;
import by.reshetnikov.proweather.BaseView;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;

/**
 * Created by SacRahl on 6/5/2017.
 */

public interface CurrentWeatherContract {

    interface View extends BaseView<CurrentWeatherPresenter> {

        void showCurrentWeather(CurrentWeatherAppModel currentWeather);
    }

    interface CurrentWeatherPresenter extends BasePresenter {
        void loadCurrentWeather();

        void setView(CurrentWeatherContract.View view);
    }
}
