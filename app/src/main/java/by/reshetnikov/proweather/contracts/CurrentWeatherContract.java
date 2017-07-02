package by.reshetnikov.proweather.contracts;


import by.reshetnikov.proweather.model.appmodels.CurrentWeatherAppModel;


public interface CurrentWeatherContract {

    interface View extends BaseView<Presenter> {

        void showCurrentWeather(CurrentWeatherAppModel currentWeather);
    }

    interface Presenter extends BasePresenter {
        void loadCurrentWeather();

        void setView(CurrentWeatherContract.View view);
    }
}
