package by.reshetnikov.proweather.ui.location.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

import by.reshetnikov.proweather.data.model.location.LocationContract;


public class LocationsDiffUtilCallback extends DiffUtil.Callback {

    private final List<LocationContract> oldLocations;
    private final List<LocationContract> newLocations;

    public LocationsDiffUtilCallback(List<LocationContract> oldList, List<LocationContract> newList) {
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
        LocationContract oldLocation = oldLocations.get(oldItemPosition);
        LocationContract newLocation = newLocations.get(oldItemPosition);

        return oldLocation.getPosition() == newLocation.getPosition() &&
                oldLocation.isCurrent() == newLocation.isCurrent();
    }

}
