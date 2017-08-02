package by.reshetnikov.proweather.contract;

import java.util.List;

import by.reshetnikov.proweather.data.model.LocationAdapterModel;

public interface LocationManagerContract {

    interface View extends ViewContract<Presenter> {

        void showSearchLocation();

        void hideSearchLocation();

        void refreshSavedLocations(List<LocationAdapterModel> savedLocations);

        void refreshSearchedLocations(List<LocationAdapterModel> savedLocations);

        void showError(String errorText);
    }

    interface Presenter extends PresenterContract {

        View getView();

        void setView(LocationManagerContract.View viewRef);

        void getSavedLocations();

        void saveLocation(LocationAdapterModel location);

        void onLocationItemMoved(int fromPosition, int toPosition);

        void onLocationItemRemoved(LocationAdapterModel location);

        void onFabClicked(boolean isAutocompleteVisible);

        void onLocationByNameSearch(String searchText);
    }
}
