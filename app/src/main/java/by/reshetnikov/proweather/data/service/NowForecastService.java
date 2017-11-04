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

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.business.nowforecastservice.ServiceForecastInteractorContract;
import by.reshetnikov.proweather.data.exception.NoLocationException;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.di.component.DaggerServiceComponent;
import by.reshetnikov.proweather.di.module.ServiceModule;
import by.reshetnikov.proweather.presentation.weather.WeatherActivity;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class NowForecastService extends JobService {

    private static final int NOTIFICATION_ID = 899041;
    @Inject
    ServiceForecastInteractorContract interactor;

    @Override
    public boolean onStartJob(JobParameters job) {
        Timber.d("onStartJob called!");
        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(getBaseContext()))
                .applicationComponent(ProWeatherApp.getProWeatherApp().getComponent())
                .build()
                .inject(this);

        interactor.getNowForecastData()
                .subscribeWith(new DisposableSingleObserver<NowForecastViewModel>() {
                    @Override
                    public void onSuccess(NowForecastViewModel nowForecast) {
                        Timber.d("NowForecastViewModel received, try to send notification");
                        sendNotification(nowForecast);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("getting forecast data failed");
                        if (e instanceof NoLocationException)
                            Timber.w("NoLocationException, getting forecast data failed");
                        else
                            Timber.e(e);
                    }
                });

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.d("onStopJob called");
        return false;
    }

    private void sendNotification(NowForecastViewModel nowForecastViewModel) {
        Timber.d("send notification called");
        String channelId = createChannel();

        Intent intent = new Intent(this, WeatherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String contentText = new StringBuilder(nowForecastViewModel.getLocationName())
                .append(": ")
                .append(nowForecastViewModel.getWeatherDescription())
                .append(", ")
                .append(nowForecastViewModel.getTemperature())
                .toString();

        Notification notification =
                new NotificationCompat.Builder(getBaseContext(), channelId)
                        .setContentTitle(getBaseContext().getString(R.string.app_name))
                        .setContentText(contentText)
                        .setSmallIcon(R.drawable.ic_umbrella)
                        .setAutoCancel(false)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationManagerCompat.IMPORTANCE_LOW)
                        .setOnlyAlertOnce(true)
                        .build();

        NotificationManagerCompat.from(getBaseContext()).notify(NOTIFICATION_ID, notification);
    }

    private String createChannel() {
        String channelId = getString(R.string.app_package_name);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return channelId;

        NotificationManager notificationManager =
                (NotificationManager) getBaseContext().getSystemService(getBaseContext().NOTIFICATION_SERVICE);

        NotificationChannel channel = notificationManager.getNotificationChannel(channelId);
        if (channel == null) {
            channel = new NotificationChannel(channelId, getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
            channel.setDescription(getString(R.string.description_for_notification_channel));
            notificationManager.createNotificationChannel(channel);
        }

        return channelId;
    }
}
