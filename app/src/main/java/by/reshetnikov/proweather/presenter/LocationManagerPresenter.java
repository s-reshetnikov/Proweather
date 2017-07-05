package by.reshetnikov.proweather.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import io.reactivex.disposables.CompositeDisposable;

public class LocationManagerPresenter implements LocationManagerContract.Presenter {

    private final static String TAG = LocationManagerPresenter.class.getSimpleName();

    @Inject
    DataRepository dataRepository;

    private LocationManagerContract.View view;
    private CompositeDisposable compositeDisposable;
    private Context appContext;


    public LocationManagerPresenter() {
        this.appContext = ProWeatherApp.getAppContext();
        ((ProWeatherApp) appContext.getApplicationContext()).getAppComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        compositeDisposable.clear();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onError() {

    }

    @Override
    public List<LocationAppModel> getLocations() {
        return dataRepository.getSavedLocations().blockingGet();
    }

    @Override
    public List<LocationAppModel> getLocationsByName(String searchText, int count) {
        return null;
    }

    @Override
    public void setView(LocationManagerContract.View view) {
        this.view = view;
    }

    @Override
    public void saveLocation(LocationAppModel location) {
        dataRepository.saveLocation(location);
    }

    @Override
    public void removeLocation(LocationAppModel location) {

    }

    @Override
    public void makeDefault(LocationAppModel location) {

    }

}
