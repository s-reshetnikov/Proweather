package by.reshetnikov.proweather.presentation.location;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.base.PresenterContract;
import by.reshetnikov.proweather.presentation.base.ViewContract;

public interface LocationManagerContract {

    interface View extends ViewContract<Presenter> {

        void showSearchLocation();

        void hideSearchLocation();

        void refreshSavedLocations(List<LocationEntity> savedLocations);

        void refreshSearchedLocations(List<LocationEntity> savedLocations);

        void showError(String errorText);
    }

    interface Presenter extends PresenterContract {

        View getView();

        void setView(LocationManagerContract.View viewRef);

        void getSavedLocations();

        void saveLocation(LocationEntity location);

        void onLocationItemMoved(int fromPosition, int toPosition);

        void onLocationItemRemoved(LocationEntity location);

        void onFabClicked(boolean isAutocompleteVisible);

        void onLocationByNameSearch(String searchText);
    }
}
