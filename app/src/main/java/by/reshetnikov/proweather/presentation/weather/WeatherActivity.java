package by.reshetnikov.proweather.presentation.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import javax.inject.Inject;

import by.reshetnikov.proweather.BuildConfig;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.service.NowForecastService;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.di.qualifier.job.ImmediateForecast;
import by.reshetnikov.proweather.di.qualifier.job.IntervalLocation;
import by.reshetnikov.proweather.presentation.location.LocationActivity;
import by.reshetnikov.proweather.presentation.settings.SettingsActivity;
import by.reshetnikov.proweather.utils.AppConstants;
import by.reshetnikov.proweather.utils.PermissionUtils;
import by.reshetnikov.proweather.utils.ToastUtils;
import timber.log.Timber;


public class WeatherActivity extends AppCompatActivity
        implements WeatherContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int PERMISSION_REQUEST_CODE = 14091;

    @Inject
    WeatherContract.Presenter presenter;
    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;
    @Inject
    @ImmediateForecast
    Job immediateForecastJob;
    @Inject
    @IntervalLocation
    Job intervalLocationJob;

    private ForecastSectionsPagerAdapter forecastSectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate() start");
        setContentView(R.layout.activity_weather);

        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((ProWeatherApp) getApplication()).getComponent())
                .build()
                .inject(this);

        presenter.setView(this);
        forecastSectionsPagerAdapter = new ForecastSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(forecastSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.location_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.weather_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        presenter.create();
        Timber.d("onCreate() end");
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.weather_view);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Timber.d("close drawer");
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            presenter.onSettingsClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startSettingsActivity() {
        Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsActivityIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_settings: {
                presenter.onSettingsClicked();
                break;
            }
            case R.id.nav_manage_location: {
                presenter.onManageLocationsClicked();
                break;
            }
        }
        DrawerLayout drawer = findViewById(R.id.weather_view);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean hasLocationPermissions() {
        return PermissionUtils.isFineLocationGranted() && PermissionUtils.isCoarseLocationGranted();
    }

    @Override
    public void requestLocationPermission() {

        final String[] permissions = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
            final String permissionsRequestMessage = getString(R.string.location_needed_to_locate_position);

            Snackbar.make(this.findViewById(R.id.weather_view), permissionsRequestMessage, Snackbar.LENGTH_LONG)
                    .setAction(R.string.grant_snackbar, new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(WeatherActivity.this, permissions, PERMISSION_REQUEST_CODE);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void startLocationActivity() {
        Intent locationManagerActivityIntent = new Intent(this, LocationActivity.class);
        startActivity(locationManagerActivityIntent);
    }

    @Override
    public void showPermissionDenied() {
        Snackbar.make(this.findViewById(R.id.weather_view), R.string.location_permission_not_granated, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings_snackbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onOpenAppPermissionsClicked();
                        openApplicationSettings();
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int result : grantResults)
                allowed = allowed && result == PackageManager.PERMISSION_GRANTED;
        }
        presenter.onLocationPermissionsResult(allowed);
    }

    @Override
    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void showGrantPermissionsInSettingsMessage() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.open_and_grant_location_permission, Toast.LENGTH_LONG);
        ToastUtils.showToast(toast);
    }

    @Override
    public void startNowForecastService() {
        Timber.d("try to start forecast service");

        firebaseJobDispatcher.mustSchedule(
                firebaseJobDispatcher.newJobBuilder()
                        .setService(NowForecastService.class)
                        .setTag("NowForecastService")
                        .setRecurring(true)
                        .setTrigger(Trigger.executionWindow(AppConstants.FORECAST_SERVICE_WINDOW_START,
                                AppConstants.FORECAST_SERVICE_WINDOW_END))
                        .setReplaceCurrent(true)
                        .build()
        );
        Timber.d("service should be started");
    }

    @Override
    public void startNowForecastServiceImmediate() {
        Timber.d("try to start forecast job");
        firebaseJobDispatcher.mustSchedule(immediateForecastJob);
        Timber.d("job started");
    }

    @Override
    public void startLocationService() {
        Timber.d("try to start location job");
        firebaseJobDispatcher.mustSchedule(intervalLocationJob);
        Timber.d("job started");
    }

    @Override
    public boolean checkPlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int status = apiAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void tryToResolveGooglePlayServiceAvailabilityError() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int status = apiAvailability.isGooglePlayServicesAvailable(getApplicationContext());

        if (apiAvailability.isUserResolvableError(status)) {
            apiAvailability.getErrorDialog(this, status, 1).show();
        } else {
            Snackbar.make(viewPager, R.string.play_services_unavailiable, Snackbar.LENGTH_INDEFINITE).show();
        }

    }

}

