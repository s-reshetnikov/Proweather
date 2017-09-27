package by.reshetnikov.proweather.business.map;

import android.location.Location;

import java.util.List;

import javax.inject.Inject;

import by.reshetnikov.proweather.data.DataContract;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.data.exception.NoLocationException;
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
    public MapInteractor(DataContract dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Single<LocationEntity> getActualLocation() {
       // if (dataManager.canUseCurrentLocation())
            return getCurrentLocation();

        //return dataManager.getChosenLocation();
    }

    @Override
    public Single<List<LocationEntity>> getAllSavedLocations() {
        return dataManager.getSavedLocations();
    }

    @Override
    public Single<LocationEntity> getCurrentLocation() {
        Timber.d("getLastLocation in interactor");
        return dataManager.getLastLocation()
                .firstOrError()
                .flatMap(new Function<Location, SingleSource<List<LocationEntity>>>() {
                    @Override
                    public SingleSource<List<LocationEntity>> apply(Location location) throws Exception {
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

    private Single<LocationEntity> getChosenLocation() {
        return dataManager.getChosenLocation();
    }


}
