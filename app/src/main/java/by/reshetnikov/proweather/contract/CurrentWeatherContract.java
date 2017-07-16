package by.reshetnikov.proweather.contract;


import by.reshetnikov.proweather.data.model.CurrentWeatherModel;


public interface CurrentWeatherContract {

    interface View extends BaseView<Presenter> {

        void showCurrentWeather(CurrentWeatherModel currentWeather);
    }

    interface Presenter extends BasePresenter {
        void loadCurrentWeather();

        void setView(CurrentWeatherContract.View view);
    }
}
