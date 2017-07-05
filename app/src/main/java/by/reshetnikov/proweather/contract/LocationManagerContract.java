package by.reshetnikov.proweather.contract;

import java.util.List;

import by.reshetnikov.proweather.model.appmodels.LocationAppModel;

public interface LocationManagerContract {

    interface View extends BaseView<LocationManagerContract.Presenter> {

        void showSearchLocation();

        void hideSearchLocation();
    }

    interface Presenter extends BasePresenter {

        List<LocationAppModel> getLocations();

        List<LocationAppModel> getLocationsByName(String searchText, int count);

        void setView(LocationManagerContract.View view);

        void saveLocation(LocationAppModel location);

        void removeLocation(LocationAppModel location);

        void makeDefault(LocationAppModel location);


    }
}
