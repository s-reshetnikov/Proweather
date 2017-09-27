package by.reshetnikov.proweather.locationmanager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import by.reshetnikov.proweather.business.locationmanager.LocationManagerInteractor;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerContract;
import by.reshetnikov.proweather.presentation.location.locationmanager.LocationManagerPresenter;
import by.reshetnikov.proweather.utils.scheduler.ImmediateSchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationManagerPresenterTest {

    private static final int TIME_OUT = 5000;

    @Mock
    private LocationManagerInteractor interactor;

    @Mock
    private LocationManagerContract.View view;

    @Captor
    private ArgumentCaptor<List<LocationEntity>> adapterModelsCaptor;

    private LocationManagerContract.Presenter presenter;


    @Before
    public void setupPresenterTest() {
        MockitoAnnotations.initMocks(this);
        presenter = new LocationManagerPresenter(interactor, ImmediateSchedulerProvider.getInstance(), new CompositeDisposable());
        presenter.setView(view);
    }

    @Test
    public void onStartTest() {
        List<LocationEntity> locations = getMockedLocations();
        when(interactor.getSavedLocations()).thenReturn(Single.just(locations));
        presenter.start();
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test(expected = NullPointerException.class)
    public void onStartErrorTest() {
//        List<LocationEntity> locations = getMockedLocations();
        when(presenter.getView()).thenThrow(new NullPointerException());
        doThrow(new NullPointerException()).when(presenter).getView();
        presenter.getView();
        verify(view, never()).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void getSavedLocationsErrorTest() {
        List<LocationEntity> locations = getMockedLocations();
        when(interactor.getSavedLocations()).thenReturn(new Single<List<LocationEntity>>() {

            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super List<LocationEntity>> observer) {
                observer.onError(new RuntimeException());
            }
        });
        presenter.onLocationsRefreshed();
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void loadLocationsFromRepositoryAndLoadIntoViewTest() throws Exception {
        List<LocationEntity> locations = getMockedLocations();
        when(interactor.getSavedLocations()).thenReturn(Single.just(locations));
        presenter.onLocationsRefreshed();
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void addLocationWhenSearchTextViewVisibleTest() {
        presenter.onFabClicked(true);
        verify(view).hideSearchLocation();
    }

    @Test
    public void addLocationWhenSearchTextViewInvisibleTest() {
        presenter.onFabClicked(false);
        verify(view).showSearchLocation();
    }

    @Test
    public void saveLocationSuccessfullyTest() {
        List<LocationEntity> locations = getMockedLocations();
        LocationEntity location = getMockedLocation();
        when(interactor.saveLocation(location))
                .thenReturn(Completable.complete());
        when(interactor.getSavedLocations()).thenReturn(Single.just(locations));
        presenter.onLocationFromDropDownClicked(location);
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void saveLocationNullErrorTest() {
        presenter.onLocationFromDropDownClicked(null);
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void saveLocationOnSaveErrorTest() {
        LocationEntity adapterModel = getMockedLocation();
        when(interactor.saveLocation(adapterModel))
                .thenReturn(new Completable() {
                    @Override
                    protected void subscribeActual(CompletableObserver completableObserver) {
                        completableObserver.onError(new RuntimeException());
                    }
                });
        presenter.onLocationFromDropDownClicked(adapterModel);
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void onLocationItemMovedSuccessfullyTest() {
        int from = 0, to = 5;
        List<LocationEntity> locations = getMockedLocations();
        when(interactor.updateLocationPosition(from, to))
                .thenReturn(Completable.complete());
        when(interactor.getSavedLocations()).thenReturn(Single.just(locations));
        presenter.onLocationItemMoved(from, to);
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void onLocationItemMovedErrorTest() {
        int from = 0, to = 5;
        List<LocationEntity> locations = getMockedLocations();
        when(interactor.updateLocationPosition(from, to))
                .thenReturn(new Completable() {
                    @Override
                    protected void subscribeActual(CompletableObserver completableObserver) {
                        completableObserver.onError(new RuntimeException());
                    }
                });
        when(interactor.getSavedLocations()).thenReturn(Single.just(locations));
        presenter.onLocationItemMoved(from, to);
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showError(Mockito.anyString());
        inOrder.verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }


    @Test
    public void removeLocationSuccessfullyTest() {
        List<LocationEntity> locations = getMockedLocations();
        LocationEntity location = getMockedLocation();
        when(interactor.removeLocation(location))
                .thenReturn(Completable.complete());
        when(interactor.getSavedLocations()).thenReturn(Single.just(locations));
        presenter.onLocationItemRemoved(location);
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void removeLocationWithErrorTest() {
        List<LocationEntity> locations = getMockedLocations();
        LocationEntity location = getMockedLocation();
        when(interactor.removeLocation(location))
                .thenReturn(new Completable() {
                    @Override
                    protected void subscribeActual(CompletableObserver s) {
                        s.onError(new RuntimeException());
                    }
                });
        when(interactor.getSavedLocations()).thenReturn(Single.just(locations));
        presenter.onLocationItemRemoved(location);
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void onLocationByNameSearchTest() {
        List<LocationEntity> locations = getMockedLocations();
        String searchText = Mockito.anyString();
        when(interactor.findLocation(searchText))
                .thenReturn(Single.just(locations));
        presenter.onSearchLocationByName(searchText);
        verify(view).refreshSearchedLocations(adapterModelsCaptor.capture());
    }

    @After
    public void onStop() {
        presenter.stop();
    }

    private List<LocationEntity> getMockedLocations() {
        List<LocationEntity> locations = Mockito.mock(List.class);
        return locations;
    }

    private LocationEntity getMockedLocation() {
        LocationEntity location = Mockito.mock(LocationEntity.class);
        return location;
    }
}
