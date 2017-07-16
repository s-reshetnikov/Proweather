package by.reshetnikov.proweather.presenter;

import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LocationManagerPresenter implements LocationManagerContract.Presenter {

    private final static String TAG = LocationManagerPresenter.class.getSimpleName();
    private final static int AUTO_COMPLETE_MAX_RESULTS = 7;
    @Inject
    DataRepository dataRepository;

    private WeakReference<LocationManagerContract.View> viewRef;
    private CompositeDisposable disposables;
    private Context appContext;

    public LocationManagerPresenter() {
        this.appContext = ProWeatherApp.getAppContext();
        ((ProWeatherApp) appContext.getApplicationContext()).getAppComponent().inject(this);
        disposables = new CompositeDisposable();
    }

    @Override
    public void start() {
        updateSavedLocations();
    }

    @Override
    public void stop() {
        disposables.dispose();
    }

    @Override
    public void saveLocation(LocationAdapterModel location) {
        disposables.add(dataRepository.saveNewLocation(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean isSaved) {
                        Log.d(TAG, "saveLocation onNext " + isSaved);
                        updateSavedLocations();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "saveLocation", e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("location saved");
                    }
                }));
    }

    @Override
    public void onLocationItemMoved(final int fromPosition, final int toPosition) {

        disposables.add(dataRepository.updateLocationPosition(fromPosition, toPosition)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean isSuccess) {
                        Log.d(TAG, "onMoveLocationItem onNext " + isSuccess);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onMoveLocationItem", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onMoveLocationItem onComplete ");
                        updateSavedLocations();

                    }
                })
        );
    }

    @Override
    public void onLocationItemRemoved(LocationAdapterModel location) {
        disposables.add(dataRepository.removeLocation(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean isCompleted) {
                        Log.d(TAG, "onLocationItemRemoved, onNext: " + isCompleted);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onLocationItemRemoved, onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onLocationItemRemoved, onComplete");
                        updateSavedLocations();
                    }
                }));
    }

    @Override
    public void onFabClicked(boolean isAutocompleteVisible) {
        if (isAutocompleteVisible)
            getView().hideSearchLocation();
        else
            getView().showSearchLocation();
    }

    @Override
    public List<LocationAdapterModel> getLocationsByName(String searchText) {
        return dataRepository.getAllLocationsByName(searchText, AUTO_COMPLETE_MAX_RESULTS).blockingSingle();
    }

    private void updateSavedLocations() {
        Log.d(TAG, "updateSavedLocations();");
        disposables.add(dataRepository.getSavedLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<LocationAdapterModel>>() {
                    @Override
                    public void onNext(@NonNull List<LocationAdapterModel> locations) {
                        Log.d(TAG, "updateSavedLocations(); onNext");
                        getView().refreshSavedLocations(locations);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "updateSavedLocations(); onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "updateSavedLocations(); onComplete");
                    }
                }));
    }

    private LocationManagerContract.View getView() {
        if (viewRef != null)
            return viewRef.get();
        throw new NullPointerException("LocationManagerContract.View is null!!!");
    }

    @Override
    public void setView(LocationManagerContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

}
