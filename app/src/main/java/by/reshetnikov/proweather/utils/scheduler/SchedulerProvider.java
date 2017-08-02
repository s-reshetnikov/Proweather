package by.reshetnikov.proweather.utils.scheduler;

import io.reactivex.Scheduler;

/**
 * Created by SacRahl on 7/26/2017.
 */

public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
