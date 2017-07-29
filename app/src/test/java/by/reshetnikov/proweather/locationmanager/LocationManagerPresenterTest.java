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

import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.presenter.LocationManagerPresenter;
import by.reshetnikov.proweather.utils.scheduler.ImmediateSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.Observer;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationManagerPresenterTest {

    private static final int TIME_OUT = 5000;

    @Mock
    private DataContract repository;

    @Mock
    private LocationManagerContract.View view;

    @Captor
    private ArgumentCaptor<List<LocationAdapterModel>> adapterModelsCaptor;

    private LocationManagerContract.Presenter presenter;


    @Before
    public void setupPresenterTest() {
        MockitoAnnotations.initMocks(this);
        presenter = new LocationManagerPresenter(repository, ImmediateSchedulerProvider.getInstance());
        presenter.setView(view);
    }

    @Test
    public void onStartTest() {
        List<LocationAdapterModel> locations = getMockedLocations();
        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        presenter.start();
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test(expected = NullPointerException.class)
    public void onStartErrorTest() {
        List<LocationAdapterModel> locations = getMockedLocations();
//        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        when(presenter.getView()).thenThrow(new NullPointerException());
        doThrow(new NullPointerException()).when(presenter).getView();
        presenter.getView();
        verify(view, never()).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void getSavedLocationsErrorTest() {
        List<LocationAdapterModel> locations = getMockedLocations();
        when(repository.getSavedLocations()).thenReturn(new Observable<List<LocationAdapterModel>>() {
            @Override
            protected void subscribeActual(Observer<? super List<LocationAdapterModel>> observer) {
                observer.onError(new RuntimeException());
            }
        });
        presenter.getSavedLocations();
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void loadLocationsFromRepositoryAndLoadIntoViewTest() throws Exception {
        List<LocationAdapterModel> locations = getMockedLocations();
        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        presenter.getSavedLocations();
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
        List<LocationAdapterModel> locations = getMockedLocations();
        LocationAdapterModel location = getMockedLocation();
        when(repository.saveNewLocation(location))
                .thenReturn(Observable.just(Boolean.TRUE));
        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        presenter.saveLocation(location);
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void saveLocationNullErrorTest() {
        presenter.saveLocation(null);
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void saveLocationOnSaveErrorTest() {
        LocationAdapterModel adapterModel = getMockedLocation();
        when(repository.saveNewLocation(adapterModel))
                .thenReturn(new Observable<Boolean>() {
                    @Override
                    protected void subscribeActual(Observer<? super Boolean> observer) {
                        observer.onError(new RuntimeException());
                    }
                });
        presenter.saveLocation(adapterModel);
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void onLocationItemMovedSuccessfullyTest() {
        int from = 0, to = 5;
        List<LocationAdapterModel> locations = getMockedLocations();
        when(repository.updateLocationPosition(from, to))
                .thenReturn(Observable.just(Boolean.TRUE));
        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        presenter.onLocationItemMoved(from, to);
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void onLocationItemMovedErrorTest() {
        int from = 0, to = 5;
        List<LocationAdapterModel> locations = getMockedLocations();
        when(repository.updateLocationPosition(from, to))
                .thenReturn(new Observable<Boolean>() {
                    @Override
                    protected void subscribeActual(Observer<? super Boolean> observer) {
                        observer.onError(new RuntimeException());
                    }
                });
        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        presenter.onLocationItemMoved(from, to);
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showError(Mockito.anyString());
        inOrder.verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }


    @Test
    public void removeLocationSuccessfullyTest() {
        List<LocationAdapterModel> locations = getMockedLocations();
        LocationAdapterModel location = getMockedLocation();
        when(repository.removeLocation(location))
                .thenReturn(Observable.just(Boolean.TRUE));
        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        presenter.onLocationItemRemoved(location);
        verify(view).refreshSavedLocations(adapterModelsCaptor.capture());
    }

    @Test
    public void removeLocationWithErrorTest() {
        List<LocationAdapterModel> locations = getMockedLocations();
        LocationAdapterModel location = getMockedLocation();
        when(repository.removeLocation(location))
                .thenReturn(new Observable<Boolean>() {
                    @Override
                    protected void subscribeActual(Observer<? super Boolean> observer) {
                        observer.onError(new RuntimeException());
                    }
                });
        when(repository.getSavedLocations()).thenReturn(Observable.just(locations));
        presenter.onLocationItemRemoved(location);
        verify(view).showError(Mockito.anyString());
    }

    @Test
    public void onLocationByNameSearchTest() {
        List<LocationAdapterModel> locations = getMockedLocations();
        String searchText = Mockito.anyString();
        when(repository.getAllLocationsByName(searchText, Mockito.anyInt()))
                .thenReturn(Observable.just(locations));
        presenter.onLocationByNameSearch(searchText);
        verify(view).refreshSearchedLocations(adapterModelsCaptor.capture());
    }

    @After
    public void onStop() {
        presenter.stop();
    }

    private List<LocationAdapterModel> getMockedLocations() {
        List<LocationAdapterModel> locations = Mockito.mock(List.class);
        return locations;
    }

    private LocationAdapterModel getMockedLocation() {
        LocationAdapterModel location = Mockito.mock(LocationAdapterModel.class);
        return location;
    }
}
