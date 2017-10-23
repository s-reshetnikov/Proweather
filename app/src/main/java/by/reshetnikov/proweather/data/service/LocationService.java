package by.reshetnikov.proweather.data.service;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class LocationService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        Timber.d("onStartJob called");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.d("onStopJob called");
        return false;
    }
}
