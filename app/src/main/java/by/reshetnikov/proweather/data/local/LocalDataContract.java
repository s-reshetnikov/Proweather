package by.reshetnikov.proweather.data.local;


import java.util.List;

import by.reshetnikov.proweather.data.AppDataContract;
import by.reshetnikov.proweather.data.appmodels.CityAppModel;
import by.reshetnikov.proweather.data.appmodels.LocationAppModel;
import by.reshetnikov.proweather.data.appmodels.UnitsAppModel;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataContract extends AppDataContract {

    Single<List<CityAppModel>> getSavedCities();

    void saveCity(CityAppModel city);

    boolean canUseCurrentLocation();

    LocationAppModel getCurrentLocation();

    Observable<UnitsAppModel> getUnits();
}
