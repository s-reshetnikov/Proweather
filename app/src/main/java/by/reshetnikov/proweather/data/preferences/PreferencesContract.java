package by.reshetnikov.proweather.data.preferences;

import by.reshetnikov.proweather.data.model.Coordinates;
import by.reshetnikov.proweather.data.model.unit.Units;
import io.reactivex.Completable;
import io.reactivex.Single;


public interface PreferencesContract {

    boolean getCanUseCurrentLocationPreference();

    Units getUnits();

    boolean canUseLatestLocation();

    Completable saveLastCoordinates(Coordinates coordinates);

    Single<Coordinates> getLastSavedCoordinates();
}
