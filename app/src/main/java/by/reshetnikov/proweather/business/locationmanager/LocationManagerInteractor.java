package by.reshetnikov.proweather.business.locationmanager;

import java.util.List;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by s-reshetnikov.
 */

public class LocationManagerInteractor implements LocationManagerInteractorContract {
    private DataContract dataManager;

    public LocationManagerInteractor(DataContract dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Single<List<LocationEntity>> getSavedLocations() {
        return dataManager.getSavedLocations();
    }

    @Override
    public Single<List<LocationEntity>> findLocation(String input) {
        return null;
    }

    @Override
    public Completable saveLocation(LocationEntity location) {
        return null;
    }

    @Override
    public Completable removeLocation(LocationEntity location) {
        return null;
    }

    @Override
    public Completable updateLocationPosition(int fromPosition, int toPosition) {
        return null;
    }
}
