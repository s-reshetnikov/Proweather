package by.reshetnikov.proweather.presentation.nowforecast;


import by.reshetnikov.proweather.data.model.weather.nowforecast.HourlyChartData;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.presentation.base.PresenterContract;


public interface NowForecastContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showCurrentWeather(NowForecastViewModel currentWeather);

        void updateTemperatureChartData(HourlyChartData chartData);

        void showLocationManager();

        void showTurnInternetOn();
    }

    interface Presenter extends PresenterContract {

        void swipedToRefresh();

        void setView(NowForecastContract.View view);
    }
}
