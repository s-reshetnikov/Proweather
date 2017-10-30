package by.reshetnikov.proweather.business.map;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.exception.NoLocationException;
import by.reshetnikov.proweather.data.model.Coordinates;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Created by s-reshetnikov.
 */

public class MapInteractor implements MapInteractorContract {

    private static final int RESULTS_NUMBER = 1;
    private DataContract dataManager;

    @Inject
    MapInteractor(DataContract dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public boolean canUseCurrentLocation() {
        return dataManager.canUseCurrentLocation();
    }


    @Override
    public Single<List<LocationEntity>> getAllSavedLocations() {
        return dataManager.getSavedLocations();
    }

    @Override
    public Single<LocationEntity> getLocationByCoordinates(double latitude, double longitude) {
        int resultsNumber = 5;
        return dataManager.getLocationsByCoordinates(latitude, longitude, resultsNumber)
                .flatMap(new Function<List<LocationEntity>, SingleSource<LocationEntity>>() {
                    @Override
                    public SingleSource<LocationEntity> apply(List<LocationEntity> locationEntities) throws Exception {
                        LocationEntity location = getNearestLocation(locationEntities, latitude, longitude);
                        location.setLatitude(latitude);
                        location.setLongitude(longitude);
                        return Single.just(location);
                    }
                })
                .flatMap(new Function<LocationEntity, SingleSource<LocationEntity>>() {
                    @Override
                    public SingleSource<LocationEntity> apply(LocationEntity locationEntity) throws Exception {
                        return dataManager.saveOrUpdateLocation(locationEntity).toSingle(new Callable<LocationEntity>() {
                            @Override
                            public LocationEntity call() throws Exception {
                                return locationEntity;
                            }
                        });
                    }
                });
    }

    @Override
    public Single<LocationEntity> getCurrentLocation() {
        return dataManager.getLowPowerLastCoordinates()
                .firstOrError()
                .flatMap(new Function<Coordinates, SingleSource<List<LocationEntity>>>() {
                    @Override
                    public SingleSource<List<LocationEntity>> apply(Coordinates location) {
                        return dataManager.getLocationsByCoordinates(location.getLatitude(), location.getLongitude(), RESULTS_NUMBER);
                    }
                })
                .flatMap(new Function<List<LocationEntity>, SingleSource<LocationEntity>>() {
                    @Override
                    public SingleSource<LocationEntity> apply(List<LocationEntity> locationEntities) throws Exception {
                        Timber.d("size of locations list searched with coordinates is " + locationEntities.size());
                        if (locationEntities.size() > 0)
                            return Single.just(locationEntities.get(0));
                        return Single.error(new NoLocationException());
                    }
                });
    }


    private LocationEntity getNearestLocation(List<LocationEntity> locationEntities, double latitude, double longitude) {
        double[] results = new double[locationEntities.size()];
        double summary = Math.abs(longitude) + Math.abs(latitude);
        for (int index = 0; index < locationEntities.size(); index++) {
            LocationEntity entity = locationEntities.get(index);
            double currentLocationSummary = Math.abs(entity.getLatitude()) + Math.abs(entity.getLongitude());
            results[index] = Math.abs(summary - currentLocationSummary);
        }
        return getNearest(locationEntities, results);
    }

    private LocationEntity getNearest(List<LocationEntity> locationEntities, double[] results) {
        int minIndex = 0;
        for (int index = 1; index < results.length; index++) {
            if (results[index] < results[minIndex])
                minIndex = index;
        }
        return locationEntities.get(minIndex);
    }

    private Single<LocationEntity> getChosenLocation() {
        return dataManager.getChosenLocation();
    }


}
