package by.reshetnikov.proweather.business.weather;


import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.utils.scheduler.ThreadSchedulerProvider;

/**
 * Created by s-reshetnikov.
 */

public class WeatherInteractor implements WeatherInteractorContract {

    private DataContract dataManager;
    private ThreadSchedulerProvider scheduler;

    @Inject
    public WeatherInteractor(DataContract dataManager, ThreadSchedulerProvider scheduler) {
        this.dataManager = dataManager;
        this.scheduler = scheduler;
    }

    @Override
    public boolean canUseCurrentLocation() {
        return dataManager.canUseCurrentLocation();
    }

}
