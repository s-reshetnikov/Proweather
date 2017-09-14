package by.reshetnikov.proweather.business.locationmanager;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.utils.scheduler.SchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class LocationManagerInteractor implements LocationManagerInteractorContract {

    private final static int AUTO_COMPLETE_MAX_RESULTS = 10;

    private DataContract dataManager;
    private SchedulerProvider scheduler;

    @Inject
    public LocationManagerInteractor(DataContract dataManager, SchedulerProvider scheduler) {
        this.dataManager = dataManager;
        this.scheduler = scheduler;
    }

    @Override
    public Single<List<LocationEntity>> getSavedLocations() {
        return dataManager.getSavedLocations();
    }

    @Override
    public Single<List<LocationEntity>> findLocation(String input) {
        return dataManager.getAllLocationsByName(input, AUTO_COMPLETE_MAX_RESULTS);
    }

    @Override
    public Completable saveLocation(LocationEntity location) {
        return dataManager.saveNewLocation(location);
    }

    @Override
    public Completable removeLocation(LocationEntity location) {
        return dataManager.removeLocation(location)
                .andThen(dataManager.getSavedLocations())
                .flatMapCompletable(new Function<List<LocationEntity>, CompletableSource>() {
                    @Override
                    public CompletableSource apply(List<LocationEntity> locations) throws Exception {
                        updateLocationsPositions(locations, location);
                        Timber.d("positions updated ");
                        logNewPositions(locations);
                        return dataManager.updateLocationPositions(locations);
                    }
                });
    }

    private void logNewPositions(List<LocationEntity> locations) {
        for (LocationEntity locationEntity : locations) {
            Timber.d(locationEntity.getLocationName() + " - " + locationEntity.getPosition());
        }
    }

    private void updateLocationsPositions(List<LocationEntity> locations, LocationEntity location) {
        for (int newPosition = location.getPosition(); newPosition < locations.size(); newPosition++) {
            int positionToChange = newPosition + 1;
            for (LocationEntity entity : locations) {
                if (entity.getPosition() == positionToChange) {
                    entity.setPosition(newPosition);
                    break;
                }
            }
        }
    }

    @Override
    public Completable updateLocationPosition(final int fromPosition, final int toPosition) {
        return dataManager.getSavedLocations()
                .flatMap(new Function<List<LocationEntity>, SingleSource<List<LocationEntity>>>() {
                    @Override
                    public SingleSource<List<LocationEntity>> apply(@NonNull List<LocationEntity> locations) throws Exception {
                        List<LocationEntity> updatedLocations = updateEntitiesOrderPosition(locations, fromPosition, toPosition);
                        return Single.just(updatedLocations);
                    }
                })
                .flatMapCompletable(new Function<List<LocationEntity>, CompletableSource>() {
                    @Override
                    public CompletableSource apply(@NonNull List<LocationEntity> locations) throws Exception {
                        return dataManager.updateLocationPositions(locations);
                    }
                });
    }

    private List<LocationEntity> updateEntitiesOrderPosition(List<LocationEntity> locations, int oldPosition, int newPosition) {
        updateCollectionItemsOrder(locations, oldPosition, newPosition);
        for (LocationEntity entity : locations) {
            int newIndex = locations.indexOf(entity);
            if (newIndex != entity.getPosition()) {
                entity.setPosition(newIndex);
            }
        }
        return locations;
    }

    private void updateCollectionItemsOrder(List<LocationEntity> locations, int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            toPosition++;
        }
        Timber.d("From = " + fromPosition + "; to = " + toPosition);
        locations.add(toPosition, locations.get(fromPosition));
        int toRemovePosition = fromPosition > toPosition ? fromPosition + 1 : fromPosition;
        Timber.d("Item to remove position is " + toRemovePosition);
        locations.remove(toRemovePosition);
    }
}
