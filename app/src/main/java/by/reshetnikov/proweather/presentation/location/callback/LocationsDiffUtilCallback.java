package by.reshetnikov.proweather.presentation.location.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;


public class LocationsDiffUtilCallback extends DiffUtil.Callback {

    private final List<LocationEntity> oldLocations;
    private final List<LocationEntity> newLocations;

    public LocationsDiffUtilCallback(List<LocationEntity> oldList, List<LocationEntity> newList) {
        this.oldLocations = oldList;
        this.newLocations = newList;
    }

    @Override
    public int getOldListSize() {
        return oldLocations.size();
    }

    @Override
    public int getNewListSize() {
        return newLocations.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldLocations.get(oldItemPosition).getLocationId() == (newLocations.get(newItemPosition).getLocationId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        LocationEntity oldLocation = oldLocations.get(oldItemPosition);
        LocationEntity newLocation = newLocations.get(newItemPosition);

        return oldLocation.getLocationId() == newLocation.getLocationId();
    }

}
