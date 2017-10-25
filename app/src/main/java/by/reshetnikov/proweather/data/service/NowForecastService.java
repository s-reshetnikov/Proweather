package by.reshetnikov.proweather.data.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.business.nowforecast.NowForecastInteractorContract;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.presentation.weather.WeatherActivity;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class NowForecastService extends JobService {

    private static final String CHANNEL_ID = "by.reshetnikov.proweather";
    private static final int NOTIFICATION_ID = 899041;

    //    @Inject
    NowForecastInteractorContract nowForecastInteractor;

    @Override
    public boolean onStartJob(JobParameters job) {
        Timber.d("onStartJob called!");
        DaggerActivityComponent.builder()
                .serviceModule(new ActivityModule(getApplicationContext()))
                .applicationComponent(ProWeatherApp.getProWeatherApp().getComponent())
                .build();
//                .inject(this);
        if (nowForecastInteractor == null)
            Timber.w("failed to get data manager");
        else
            Timber.w("succeeded to get data manager");
        sendNotification();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.d("onStopJob called");
        return false;
    }

    private void sendNotification() {
        Timber.d("send notification called");
        initChannel();

        Intent intent = new Intent(this, WeatherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification =
                new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setContentTitle(getBaseContext().getString(R.string.notification_title))
                        .setContentText("Yep!")
                        .setSmallIcon(R.drawable.ic_umbrella)
                        .setNumber(5)
                        .setAutoCancel(false)
                        .setContentIntent(pendingIntent)
                        .build();

        NotificationManagerCompat.from(getBaseContext()).notify(NOTIFICATION_ID, notification);
    }

    private void initChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;
        NotificationManager notificationManager =
                (NotificationManager) getBaseContext().getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        NotificationChannel channel =
                new NotificationChannel(CHANNEL_ID, "ProWeather", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Notifications from ProWeather app");
        notificationManager.createNotificationChannel(channel);
    }
}
