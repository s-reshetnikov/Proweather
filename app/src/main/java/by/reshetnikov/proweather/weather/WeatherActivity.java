package by.reshetnikov.proweather.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
}
