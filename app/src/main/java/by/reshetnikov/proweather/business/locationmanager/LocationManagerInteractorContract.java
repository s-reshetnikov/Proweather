package by.reshetnikov.proweather.business.locationmanager;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by s-reshetnikov.
 */

public interface LocationManagerInteractorContract {

    Single<List<LocationEntity>> getSavedLocations();

    Single<List<LocationEntity>> findLocation(String input);

    Completable saveLocation(LocationEntity location);

    Completable removeLocation(LocationEntity location);

    Completable updateLocationPosition(int fromPosition, int toPosition);
}
