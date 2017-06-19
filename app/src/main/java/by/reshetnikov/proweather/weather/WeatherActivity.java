package by.reshetnikov.proweather.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.currentweather.CurrentWeatherFragment;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.settings.SettingsActivity;
import by.reshetnikov.proweather.utils.FragmentUtils;
import by.reshetnikov.proweather.utils.ToastUtils;


public class WeatherActivity extends AppCompatActivity
        implements WeatherContract.View, NavigationView.OnNavigationItemSelectedListener,
        CurrentWeatherFragment.OnFragmentInteractionListener {

    private final String TAG = WeatherActivity.class.getSimpleName();
    private final int PERMISSION_REQUEST_CODE = 1409;
    // @Inject
    WeatherContract.WeatherPresenter presenter;

    @Inject
    DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate start");
        setContentView(R.layout.activity_weather);
        //((ProWeatherApp)this.getApplication()).

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.weather_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (hasPermissions()) {
            createLocationSevice();

        } else {
            //our app doesn't have permissions, So i m requesting permissions.
            requestPermissionWithRationale();
        }

        if (findViewById(R.id.weather_fragment_placeholder) != null) {

            if (savedInstanceState != null) {
                return;
            }

            FragmentUtils.replaceFragment(getSupportFragmentManager(),
                    R.id.weather_fragment_placeholder,
                    CurrentWeatherFragment.newInstance());
        }

        Log.d(TAG, "onCreate end");
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed start");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.weather_view);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.d(TAG, "close drawer");
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Log.d(TAG, "onBackPressed end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSettingsActivity() {
        Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsActivityIntent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_settings: {
                startSettingsActivity();
                break;
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.weather_view);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCurrentWeatherFragmentInteraction(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(WeatherContract.WeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {
        Toast loadingToast = Toast.makeText(this, "Loading ...", Toast.LENGTH_LONG);
        ToastUtils.showToast(loadingToast);

    }

    @Override
    public void hideProgress() {
        Toast loadedToast = Toast.makeText(this, "Loaded!!!", Toast.LENGTH_SHORT);
        ToastUtils.showToast(loadedToast);
    }

    @Override
    public void showError(String message) {
        Toast errorToast = Toast.makeText(this, "Some error :(", Toast.LENGTH_SHORT);
        ToastUtils.showToast(errorToast);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int result : grantResults)
                allowed = allowed && result == PackageManager.PERMISSION_GRANTED;
        }

        if (allowed) {
            createLocationSevice();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast warning = Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT);
                    ToastUtils.showToast(warning);

                } else {
                    showNoLocationPermissionSnackbar();
                }
            }
        }
    }

    private void createLocationSevice() {

    }

    private void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            final String message = getString(R.string.location_needed_to_locate_position);
            Snackbar.make(this.findViewById(R.id.weather_view), message, Snackbar.LENGTH_LONG)
                    .setAction(R.string.grant_snackbar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPermissions();
                        }
                    })
                    .show();
        } else {
            requestPermissions();
        }
    }

    private boolean hasPermissions() {
        int check = 0;
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
        for (String permission : permissions) {
            check = ContextCompat.checkSelfPermission(this, permission);
            if (check == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }

    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    void showNoLocationPermissionSnackbar() {
        Snackbar.make(this.findViewById(R.id.weather_view), R.string.location_permission_not_granated, Snackbar.LENGTH_LONG)
                .setAction(R.string.settings_snackbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                R.string.grant_location_permission, Toast.LENGTH_SHORT);
                        ToastUtils.showToast(toast);
                    }
                }).show();
    }

    private void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }


}

