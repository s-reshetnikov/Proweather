package by.reshetnikov.proweather.utils.scheduler;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SacRahl on 7/26/2017.
 */

public class ImmediateThreadSchedulerProvider implements ThreadSchedulerProvider {
    private static ImmediateThreadSchedulerProvider INSTANCE;

    private ImmediateThreadSchedulerProvider() {
    }

    public static synchronized ImmediateThreadSchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImmediateThreadSchedulerProvider();
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
