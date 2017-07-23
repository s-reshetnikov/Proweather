package by.reshetnikov.proweather.contract;

import java.util.List;

import by.reshetnikov.proweather.data.model.LocationAdapterModel;

public interface LocationManagerContract {

    interface View extends BaseView<LocationManagerContract.Presenter> {

        void showSearchLocation();

        void hideSearchLocation();

        void refreshSavedLocations(List<LocationAdapterModel> savedLocations);

        void refreshSearchedLocations(List<LocationAdapterModel> savedLocations);
    }

    interface Presenter extends BasePresenter {

        void getSavedLocations();

        void setView(LocationManagerContract.View viewRef);

        void saveLocation(LocationAdapterModel location);

        void onLocationItemMoved(int fromPosition, int toPosition);

        void onLocationItemRemoved(LocationAdapterModel location);

        void onFabClicked(boolean isAutocompleteVisible);

        void onLocationByNameSearch(String searchText);
    }
}
