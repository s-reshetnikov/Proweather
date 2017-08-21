package by.reshetnikov.proweather.presentation.nowforecast;


import com.github.mikephil.charting.data.Entry;

import java.util.List;

import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.presentation.base.PresenterContract;
import by.reshetnikov.proweather.presentation.base.ViewContract;


public interface NowForecastContract {

    interface View extends ViewContract<Presenter> {

        void showLoading();

        void hideLoading();

        void showCurrentWeather(NowForecastViewModel currentWeather);

        void updateTemperatureChartData(List<Entry> weatherForecast, char unitSign, List<String> xAxisDescription);

        void showLocationManager();

        void showTurnInternetOn();
    }

    interface Presenter extends PresenterContract {

        void refreshClicked();

        void setView(NowForecastContract.View view);
    }
}
