package by.reshetnikov.proweather.presenter;

import org.greenrobot.greendao.annotation.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.utils.scheduler.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class LocationManagerPresenter implements LocationManagerContract.Presenter {

    private final static String TAG = LocationManagerPresenter.class.getSimpleName();
    private final static int AUTO_COMPLETE_MAX_RESULTS = 10;

    private DataContract dataRepository;
    private BaseSchedulerProvider scheduler;
    private WeakReference<LocationManagerContract.View> viewRef;
    private CompositeDisposable disposables;

    @Inject
    public LocationManagerPresenter(DataContract dataRepository, BaseSchedulerProvider scheduler) {
        this.dataRepository = dataRepository;
        this.scheduler = scheduler;
        disposables = new CompositeDisposable();
    }

    @Override
    public void start() {
        getSavedLocations();
    }

    @Override
    public void stop() {
        disposables.dispose();
        viewRef = null;
    }

    @Override
    public void saveLocation(@NotNull LocationAdapterModel location) {
        if (location == null) {
            getView().showError("Error on saving location");
            return;
        }

        disposables.add(dataRepository.saveNewLocation(location)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean isSaved) {
                        getSavedLocations();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Error on saving location");
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
                .subscribeOn(scheduler.io())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean isSuccess) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Location wasn't moved");
                        getSavedLocations();
                    }

                    @Override
                    public void onComplete() {
                        getSavedLocations();

                    }
                })
        );
    }

    @Override
    public void onLocationItemRemoved(@NotNull LocationAdapterModel location) {
        disposables.add(dataRepository.removeLocation(location)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean isCompleted) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Location wasn't moved");
                        getSavedLocations();
                    }

                    @Override
                    public void onComplete() {
                        getSavedLocations();
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
    public void onLocationByNameSearch(String searchText) {
        List<LocationAdapterModel> locations =
                dataRepository.getAllLocationsByName(searchText, AUTO_COMPLETE_MAX_RESULTS).blockingSingle();
        getView().refreshSearchedLocations(locations);
    }

    @Override
    public void getSavedLocations() {
        disposables.add(dataRepository.getSavedLocations()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableObserver<List<LocationAdapterModel>>() {
                    @Override
                    public void onNext(@NonNull List<LocationAdapterModel> locations) {
                        getView().refreshSavedLocations(locations);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("No locations saved");
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public LocationManagerContract.View getView() {
        if (viewRef != null)
            return viewRef.get();
        throw new NullPointerException("LocationManagerContract.View is null!!!");
    }

    @Override
    public void setView(LocationManagerContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

}
