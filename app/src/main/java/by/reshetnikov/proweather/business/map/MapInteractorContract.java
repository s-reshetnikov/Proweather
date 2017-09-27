package by.reshetnikov.proweather.business.map;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import io.reactivex.Single;

/**
 * Created by s-reshetnikov.
 */

public interface MapInteractorContract {

    Single<LocationEntity> getActualLocation();

    Single<LocationEntity> getCurrentLocation();

    Single<List<LocationEntity>> getAllSavedLocations();
}
