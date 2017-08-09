package by.reshetnikov.proweather.ui.location;

import java.util.List;

import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.ui.base.PresenterContract;
import by.reshetnikov.proweather.ui.base.ViewContract;

public interface LocationManagerContract {

    interface View extends ViewContract<Presenter> {

        void showSearchLocation();

        void hideSearchLocation();

        void refreshSavedLocations(List<LocationContract> savedLocations);

        void refreshSearchedLocations(List<LocationContract> savedLocations);

        void showError(String errorText);
    }

    interface Presenter extends PresenterContract {

        View getView();

        void setView(LocationManagerContract.View viewRef);

        void getSavedLocations();

        void saveLocation(LocationContract location);

        void onLocationItemMoved(int fromPosition, int toPosition);

        void onLocationItemRemoved(LocationContract location);

        void onFabClicked(boolean isAutocompleteVisible);

        void onLocationByNameSearch(String searchText);
    }
}
