package by.reshetnikov.proweather.contract;


import by.reshetnikov.proweather.data.model.CurrentWeatherAdapterModel;


public interface CurrentWeatherContract {

    interface View extends ViewContract<Presenter> {

        void showCurrentWeather(CurrentWeatherAdapterModel currentWeather);
    }

    interface Presenter extends PresenterContract {
        void loadCurrentWeather();

        void setView(CurrentWeatherContract.View view);
    }
}
