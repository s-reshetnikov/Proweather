package by.reshetnikov.proweather.presentation.location;

import org.greenrobot.greendao.annotation.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.exception.NoNetworkException;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class LocationManagerPresenter implements LocationManagerContract.Presenter {

    private final static String TAG = LocationManagerPresenter.class.getSimpleName();
    private final static int AUTO_COMPLETE_MAX_RESULTS = 10;

    private DataContract dataRepository;
    private SchedulerProvider scheduler;
    private WeakReference<LocationManagerContract.View> viewRef;
    private CompositeDisposable disposables;

    @Inject
    public LocationManagerPresenter(DataContract dataRepository, SchedulerProvider scheduler) {
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
    public void saveLocation(@NotNull LocationEntity location) {
        if (location == null) {
            getView().showError("Error on saving location");
            return;
        }

        disposables.add(dataRepository.saveNewLocation(location)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getSavedLocations();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Error on saving location");
                    }
                }));
    }

    @Override
    public void onLocationItemMoved(final int fromPosition, final int toPosition) {
        disposables.add(dataRepository.updateLocationPosition(fromPosition, toPosition)
                .subscribeOn(scheduler.io())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getSavedLocations();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Location wasn't moved");
                        getSavedLocations();
                    }
                })
        );
    }

    @Override
    public void onLocationItemRemoved(@NotNull LocationEntity location) {
        disposables.add(dataRepository.removeLocation(location)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getSavedLocations();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Location wasn't moved");
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
        List<LocationEntity> locations =
                dataRepository.getAllLocationsByName(searchText, AUTO_COMPLETE_MAX_RESULTS).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof NoNetworkException)
                            getView().showError("No Network");
                    }
                })
                        .blockingGet();
        getView().refreshSearchedLocations(locations);
    }

    @Override
    public void getSavedLocations() {
        disposables.add(dataRepository.getSavedLocations()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<List<LocationEntity>>() {

                    @Override
                    public void onSuccess(@NonNull List<LocationEntity> locations) {
                        getView().refreshSavedLocations(locations);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("No locations saved");
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
