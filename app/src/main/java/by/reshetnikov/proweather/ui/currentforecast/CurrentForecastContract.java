package by.reshetnikov.proweather.ui.currentforecast;


import com.github.mikephil.charting.data.Entry;

import java.util.List;

import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapterContract;
import by.reshetnikov.proweather.ui.base.PresenterContract;
import by.reshetnikov.proweather.ui.base.ViewContract;


public interface CurrentForecastContract {

    interface View extends ViewContract<Presenter> {

        void showCurrentWeather(CurrentForecastAdapterContract currentWeather);

        void updateTimeLineData(List<Entry> forecast);
    }

    interface Presenter extends PresenterContract {

        void loadCurrentWeather();

        void setView(CurrentForecastContract.View view);
    }
}
