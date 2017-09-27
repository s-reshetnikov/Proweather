package by.reshetnikov.proweather.presentation.location.locationmanager;

import org.greenrobot.greendao.annotation.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.business.locationmanager.LocationManagerInteractorContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.exception.NoNetworkException;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

public class LocationManagerPresenter implements LocationManagerContract.Presenter {

    private LocationManagerInteractorContract interactor;
    private SchedulerProvider scheduler;
    private WeakReference<LocationManagerContract.View> viewRef;
    private CompositeDisposable disposables;

    @Inject
    public LocationManagerPresenter(LocationManagerInteractorContract interactor, SchedulerProvider scheduler, CompositeDisposable disposables) {
        this.interactor = interactor;
        this.scheduler = scheduler;
        this.disposables = disposables;
    }

    @Override
    public void start() {
        onLocationsRefreshed();
    }

    @Override
    public void stop() {
        disposables.dispose();
        viewRef = null;
    }

    @Override
    public void onLocationFromDropDownClicked(@NotNull LocationEntity location) {
        if (location == null) {
            getView().showError("Error on saving location");
            return;
        }

        disposables.add(interactor.saveLocation(location)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        onLocationsRefreshed();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Error on saving location");
                    }
                }));
    }

    @Override
    public void onLocationItemMoved(final int fromPosition, final int toPosition) {
        disposables.add(interactor.updateLocationPosition(fromPosition, toPosition)
                .subscribeOn(scheduler.io())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        onLocationsRefreshed();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("LocationModel wasn't moved");
                        onLocationsRefreshed();
                    }
                })
        );
    }

    @Override
    public void onLocationItemRemoved(@NotNull LocationEntity location) {
        Timber.d("onLocationItemRemoved(), called");
        disposables.add(interactor.removeLocation(location)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        onLocationsRefreshed();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError("Location wasn't moved");
                        onLocationsRefreshed();
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
    public void onSearchLocationByName(String searchText) {
        Timber.d("onSearchLocationByName(), called");
        List<LocationEntity> locations =
                interactor.findLocation(searchText)
                        .doOnError(new Consumer<Throwable>() {
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
    public void onLocationsRefreshed() {
        Timber.d("onLocationsRefreshed(), called");
        disposables.add(interactor.getSavedLocations()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(new DisposableSingleObserver<List<LocationEntity>>() {
                    @Override
                    public void onSuccess(@NonNull List<LocationEntity> locations) {
                        Timber.d("onLocationsRefreshed(), success");
                        getView().refreshSavedLocations(locations);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d("onLocationsRefreshed(), error");
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
