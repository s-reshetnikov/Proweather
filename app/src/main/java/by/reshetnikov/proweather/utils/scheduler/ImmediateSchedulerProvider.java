package by.reshetnikov.proweather.utils.scheduler;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SacRahl on 7/26/2017.
 */

public class ImmediateSchedulerProvider implements SchedulerProvider {
    private static ImmediateSchedulerProvider INSTANCE;

    private ImmediateSchedulerProvider(){
    }

    public static synchronized ImmediateSchedulerProvider getInstance (){
        if (INSTANCE == null) {
            INSTANCE = new ImmediateSchedulerProvider();
        }
        return INSTANCE;
    }

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
