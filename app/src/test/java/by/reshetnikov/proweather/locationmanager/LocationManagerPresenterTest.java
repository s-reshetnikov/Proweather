package by.reshetnikov.proweather.locationmanager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.presenter.LocationManagerPresenter;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

public class LocationManagerPresenterTest {

    @Mock
    private DataContract repository;
    @Mock
    private LocationManagerContract.View locationManageView;

    @Captor
    private ArgumentCaptor<List<LocationAdapterModel>> adapterModelsCaptor;

    private LocationManagerContract.Presenter presenter;


    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
//        presenter = new LocationManagerPresenter();
//        presenter.setView(locationManageView);
    }

    @Test
    public void loadLocationsFromRepositoryAndLoadIntoView() {

        when(repository.getSavedLocations()).thenReturn(Observable.just(adapterModelsCaptor.capture()));
        TestObserver<List<LocationAdapterModel>> testObserver = repository.getSavedLocations().test();


        testObserver.assertNoErrors();
//        testObserver.assert
        testObserver.assertComplete();
    }

    @Test
    public void addLocation() {
        presenter.onFabClicked(true);
    }

    @After
    public void onStop() {
        presenter.stop();
    }
}
