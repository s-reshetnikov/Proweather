package by.reshetnikov.proweather.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
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
    private List<LocationAppModel> savedLocations = new ArrayList<>();
    private List<LocationAppModel> autocompleteLocations = new ArrayList<>();
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
        // return dataRepository.getSavedLocations();
        return new ArrayList<>();
    }

    @Override
    public List<LocationAppModel> getLocationsByName(String searchText, int resultsCount) {
        return new ArrayList<>();
        //dataRepository.getAllLocationsByName(searchText, resultsCount);
    }

    @Override
    public void setView(LocationManagerContract.View view) {
        this.view = view;
    }

    @Override
    public void saveLocation(LocationAppModel location) {
        dataRepository.saveNewLocation(location);
    }

    @Override
    public void removeLocation(int position) {
        //dataRepository.removeLocation(position);
    }

    @Override
    public void removeLocation(LocationAppModel location) {

    }

    @Override
    public void makeDefault(LocationAppModel location) {

    }

    @Override
    public void onLocationItemMoved(int position, int oldPosition) {
        Collections.swap(savedLocations, position, oldPosition);
        // TODO: async
        //dataRepository.saveLocation(savedLocations.get(position));
        //dataRepository.saveLocation(savedLocations.get(oldPosition));

        view.refreshSavedLocations(savedLocations);
    }

    @Override
    public void onLocationItemDeleted(int position) {

    }

    private void updateSavedLocations(List<LocationAppModel> locations) {
        savedLocations = Collections.synchronizedList(savedLocations);
        synchronized (savedLocations) {
            savedLocations.clear();
            savedLocations.addAll(locations);
            view.refreshSavedLocations(savedLocations);
        }
    }

    private void saveLocations(List<LocationAppModel> locations) {
        //dataRepository.saveLocations(locations);
    }

}
