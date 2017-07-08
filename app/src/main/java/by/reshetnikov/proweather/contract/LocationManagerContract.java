package by.reshetnikov.proweather.contract;

import java.util.List;

import by.reshetnikov.proweather.model.appmodels.LocationAppModel;

public interface LocationManagerContract {

    interface View extends BaseView<LocationManagerContract.Presenter> {

        void showSearchLocation();

        void hideSearchLocation();

        void refreshSavedLocations(List<LocationAppModel> savedLocations);
    }

    interface Presenter extends BasePresenter {

        List<LocationAppModel> getLocations();

        List<LocationAppModel> getLocationsByName(String searchText, int resultsCount);

        void setView(LocationManagerContract.View view);

        void saveLocation(LocationAppModel location);

        void removeLocation(int position);

        void removeLocation(LocationAppModel location);

        void makeDefault(LocationAppModel location);

        void onLocationItemMoved(int position, int oldPosition);

        void onLocationItemDeleted(int position);
    }
}
