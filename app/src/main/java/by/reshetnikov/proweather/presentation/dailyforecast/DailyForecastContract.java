package by.reshetnikov.proweather.presentation.dailyforecast;

import java.util.List;

import by.reshetnikov.proweather.presentation.base.PresenterContract;
import by.reshetnikov.proweather.presentation.base.ViewContract;


public interface DailyForecastContract {

    interface View extends ViewContract<Presenter> {

        void showWeatherForecast(List<DailyForecastViewModel> forecast);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends PresenterContract {

        void onRefresh();

        void setView(View view);
    }
}