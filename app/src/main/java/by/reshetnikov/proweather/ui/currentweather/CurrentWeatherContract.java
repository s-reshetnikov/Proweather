package by.reshetnikov.proweather.ui.currentweather;


import by.reshetnikov.proweather.data.model.CurrentWeatherAdapterModel;
import by.reshetnikov.proweather.ui.base.PresenterContract;
import by.reshetnikov.proweather.ui.base.ViewContract;


public interface CurrentWeatherContract {

    interface View extends ViewContract<Presenter> {

        void showCurrentWeather(CurrentWeatherAdapterModel currentWeather);
    }

    interface Presenter extends PresenterContract {
        void loadCurrentWeather();

        void setView(CurrentWeatherContract.View view);
    }
}
