package by.reshetnikov.proweather.weather;

import by.reshetnikov.proweather.BasePresenter;
import by.reshetnikov.proweather.BaseView;

/**
 * Created by SacRahl on 6/6/2017.
 */

public interface WeatherContract {
    interface View extends BaseView<WeatherPresenter> {
        void showProgress();

        void hideProgress();

        void showError(String message);
    }

    interface WeatherPresenter extends BasePresenter {
        void getCitiesList();

        void setView(WeatherContract.View view);
    }
}
